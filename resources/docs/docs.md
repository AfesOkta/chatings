Database Configuration is Required

If you haven't already, then please follow the steps below to configure your database connection and run the necessary migrations.

    Run lein run migrate in the root of the project to create the tables.
    Restart the application.

Managing Your Middleware

Request middleware functions are located under the guestbook.middleware namespace.

This namespace is reserved for any custom middleware for the application. Some default middleware is already defined here. The middleware is assembled in the wrap-base function.

Middleware used for development is placed in the guestbook.dev-middleware namespace found in the env/dev/clj/ source path.
Here are some links to get started

    HTML templating
    Accessing the database
    Serving static resources
    Setting response types
    Defining routes
    Adding middleware
    Sessions and cookies
    Security
    Deploying the application
