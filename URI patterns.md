## @RequestMapping URI patterns

|示例|说明|
|---|---|
|/resources/ima?e.png|match one character in a path segment|
|/resources/*.png|match zero or more characters in a path segment|
|/resources/**|match multiple path segments|
|/projects/{project}/versions|match a path segment and capture it as a variable|
|/projects/{project:[a-z]+}/versions|match and capture a variable with a regex|











