CREATE OR REPLACE FUNCTION handle_stage_closure()
    RETURNS TRIGGER AS
$$
BEGIN
    -- Only proceed if the stage is being closed (changed from false to true)
    IF NEW.closed = true AND OLD.closed = false THEN
        -- Update total times for all riders in this competition
        UPDATE general_results gr
        SET total_time = (SELECT SUM(sr.duration)
                          FROM stage_results sr
                                   JOIN stages s ON sr.stage_id = s.id
                          WHERE sr.rider_id = gr.rider_id
                            AND s.competition_id = gr.competition_id
                            AND s.closed = true -- Only include closed stages
        ),
            updated_at = NOW()
        WHERE gr.competition_id = NEW.competition_id;

        -- Update positions for all riders in the competition
        UPDATE general_results gr1
        SET position = (SELECT COUNT(*)
                        FROM general_results gr2
                        WHERE gr2.competition_id = gr1.competition_id
                          AND gr2.total_time <= gr1.total_time)
        WHERE gr1.competition_id = NEW.competition_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger for stage closure
DROP TRIGGER IF EXISTS stage_closure_trigger ON stages;
CREATE TRIGGER stage_closure_trigger
    AFTER UPDATE OF closed
    ON stages
    FOR EACH ROW
EXECUTE FUNCTION handle_stage_closure();