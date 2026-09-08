USE nightshift;
GO

IF OBJECT_ID('dbo.servers', 'U') IS NULL
BEGIN
CREATE TABLE dbo.servers (
                             id BIGINT IDENTITY(1,1) PRIMARY KEY,

                             hostname NVARCHAR(100) NOT NULL UNIQUE,

                             environment NVARCHAR(20) NOT NULL,

                             status NVARCHAR(20) NOT NULL
            CONSTRAINT DF_servers_status
            DEFAULT 'HEALTHY',

                             created_at DATETIME2 NOT NULL
                                 CONSTRAINT DF_servers_created_at
                                 DEFAULT SYSUTCDATETIME(),

                             CONSTRAINT CK_servers_environment
                                 CHECK (environment IN ('DEV', 'TEST', 'PROD')),

                             CONSTRAINT CK_servers_status
                                 CHECK (status IN ('HEALTHY', 'DEGRADED'))
);
END;
GO


IF OBJECT_ID('dbo.incidents', 'U') IS NULL
BEGIN
CREATE TABLE dbo.incidents (
                               id BIGINT IDENTITY(1,1) PRIMARY KEY,

                               server_id BIGINT NOT NULL,

                               title NVARCHAR(200) NOT NULL,

                               status NVARCHAR(20) NOT NULL
            CONSTRAINT DF_incidents_status
            DEFAULT 'OPEN',

                               opened_at DATETIME2 NOT NULL
                                   CONSTRAINT DF_incidents_opened_at
                                   DEFAULT SYSUTCDATETIME(),

                               resolved_at DATETIME2 NULL,

                               CONSTRAINT FK_incidents_server
                                   FOREIGN KEY (server_id)
                                       REFERENCES dbo.servers(id),

                               CONSTRAINT CK_incidents_status
                                   CHECK (status IN ('OPEN', 'RESOLVED'))
);
END;
GO