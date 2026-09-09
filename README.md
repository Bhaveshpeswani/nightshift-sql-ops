# NightShift

A small Spring Boot project I built while getting hands-on with SQL Server and basic infrastructure automation.
NightShift keeps track of database servers and incidents. Opening an incident marks a server as degraded. Once the final open incident is resolved, the server returns to healthy.

I used direct SQL through Spring JDBC rather than JPA because I wanted to spend more time working with SQL Server itself.

## What it does

- Register database servers
- Track DEV, TEST and PROD environments
- Open incidents against servers
- Mark affected servers as degraded
- Resolve incidents
- Return a server to healthy when no open incidents remain
- Generate an open-incident summary using a T-SQL stored procedure
- Check the local SQL Server Windows service with PowerShell

## Stack

- Java 21
- Spring Boot
- Spring JDBC / JdbcTemplate
- Microsoft SQL Server
- T-SQL
- PowerShell

## Database

The database contains two main tables:

- `servers`
- `incidents`

An incident belongs to one server. The schema uses primary keys, a foreign key, check constraints and an index for open-incident lookups.

SQL scripts are stored in the `sql` directory.

## Running locally

1. Create a SQL Server database named `nightshift`.
2. Run `sql/01-schema.sql`.
3. Run `sql/02-index.sql`.
4. Run `sql/03-report.sql`.
5. Configure `DB_USERNAME` and `DB_PASSWORD`.
6. Start the Spring Boot application.

The application runs at `http://localhost:8080`.

## Main endpoints

`POST /api/servers`

`GET /api/servers`

`POST /api/servers/{serverId}/incidents`

`PATCH /api/incidents/{incidentId}/resolve`

`GET /api/incidents/open`

`GET /api/reports/open-incidents`

## Current limitations

This is a learning project running against a single local SQL Server instance. It does not currently include authentication, distributed monitoring or production deployment. Thanks for reading it :)
