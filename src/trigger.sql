-- First, drop existing triggers and functions
DROP TRIGGER IF EXISTS trigger_calculate_position ON stage_results;
DROP FUNCTION IF EXISTS calculate_rider_position();

-- Create function to handle all position updates
CREATE OR REPLACE FUNCTION calculate_rider_position()
RETURNS TRIGGER AS $$
BEGIN
    -- For INSERT, set initial position and reorder all affected positions
    IF (TG_OP = 'INSERT') THEN
        -- First, update positions of existing records that should come after the new record
UPDATE stage_results
SET position = position + 1
WHERE stage_id = NEW.stage_id
  AND duration > NEW.duration;

-- Then calculate the correct position for the new record
SELECT COUNT(*) + 1 INTO NEW.position
FROM stage_results
WHERE stage_id = NEW.stage_id
  AND duration < NEW.duration;

RETURN NEW;
END IF;

    -- For UPDATE
    IF (TG_OP = 'UPDATE') THEN
        -- If duration changed
        IF NEW.duration != OLD.duration THEN
            -- First, temporarily set position to null to avoid conflicts
UPDATE stage_results
SET position = null
WHERE stage_id = NEW.stage_id
  AND rider_id = NEW.rider_id;

-- Update positions of all other records based on duration
UPDATE stage_results
SET position = subq.new_pos
    FROM (
                SELECT
                    rider_id,
                    ROW_NUMBER() OVER (ORDER BY duration) as new_pos
                FROM stage_results
                WHERE stage_id = NEW.stage_id
                AND rider_id != NEW.rider_id
            ) as subq
WHERE stage_results.stage_id = NEW.stage_id
  AND stage_results.rider_id = subq.rider_id;

-- Calculate new position for the updated record
SELECT COUNT(*) + 1 INTO NEW.position
FROM stage_results
WHERE stage_id = NEW.stage_id
  AND duration < NEW.duration;
END IF;

RETURN NEW;
END IF;

    -- For DELETE
    IF (TG_OP = 'DELETE') THEN
        -- Update positions of records that came after the deleted record
UPDATE stage_results
SET position = position - 1
WHERE stage_id = OLD.stage_id
  AND position > OLD.position;

RETURN OLD;
END IF;

RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Create BEFORE trigger
CREATE TRIGGER trigger_calculate_position
    BEFORE INSERT OR UPDATE OR DELETE ON stage_results
    FOR EACH ROW
    EXECUTE FUNCTION calculate_rider_position();