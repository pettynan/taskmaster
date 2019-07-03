# Taskmaster
App by Peter Tynan
deployed at http://taskmaster-env.f3vxumqd2q.us-west-2.elasticbeanstalk.com

## API routes
  /tasks - Takes GET request, returns JSON data of all existing tasks in database.
  /tasks - Takes POST request with 'title', 'description', and (optional) 'assignee' parameters, and creates a new task using those parameters.
  /tasks/{id}/state - takes PUT request, advances the current status of the task with that id, in the order of Available -> Assigned -> Accepted -> Finished
  /users/{name}/tasks - takes GET request, returns JSON data of all tasks in database assigned to specified name.
  /tasks/{id}/assign/{assignee} - takes PUT request, assigns specified name (assignee) to the task with specified id.

## Technologies used
  AWS DynamoDB
  AWS ElasticBeanstalk
  Spring
  Gradle
  