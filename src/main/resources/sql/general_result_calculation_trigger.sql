ALTER TABLE general_results
    ADD COLUMN IF NOT EXISTS total_time bigint,
    ADD COLUMN IF NOT EXISTS position integer;

-- Create or replace the function
CREATE OR REPLACE FUNCTION update_general_result()
RETURNS TRIGGER AS $$
BEGIN
    -- Update total time for the affected rider
UPDATE general_results gr
SET total_time = (
    SELECT SUM(sr.duration)
    FROM stage_results sr
             JOIN stages s ON sr.stage_id = s.id
    WHERE sr.rider_id = NEW.rider_id
      AND s.competition_id = gr.competition_id
)
WHERE gr.rider_id = NEW.rider_id
  AND gr.competition_id = (
    SELECT competition_id
    FROM stages
    WHERE id = NEW.stage_id
);

-- Update positions for all riders in the affected competition
UPDATE general_results gr1
SET position = (
    SELECT COUNT(*)
    FROM general_results gr2
    WHERE gr2.competition_id = gr1.competition_id
      AND gr2.total_time <= gr1.total_time
)
WHERE gr1.competition_id = (
    SELECT competition_id
    FROM stages
    WHERE id = NEW.stage_id
);

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create the trigger
CREATE TRIGGER general_result_update_trigger
    AFTER INSERT OR UPDATE ON stage_results
                        FOR EACH ROW
                        EXECUTE FUNCTION update_general_result();