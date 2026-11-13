CREATE TABLE project
(
    id   UUID PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

-- Until now, an expense stored the name of its project.
-- Create a project for each of those names, and refer to it by id instead.
INSERT INTO project (id, name)
SELECT gen_random_uuid(), project
FROM expense
WHERE project IS NOT NULL
GROUP BY project;

ALTER TABLE expense RENAME COLUMN project TO project_name;
ALTER TABLE expense ADD COLUMN project UUID REFERENCES project (id);

UPDATE expense
SET project = project.id
FROM project
WHERE project.name = expense.project_name;

ALTER TABLE expense DROP COLUMN project_name;
