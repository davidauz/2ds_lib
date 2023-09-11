<h1>Two data sources in a library module</h1>

<p>This project shows a library module that defines two different data sources.
</p><p>
Another module uses the library to actually access the data.
</p> <p>
Use this script on your MSSQL server to create the test environment:
</p>
<pre>
use [master];
CREATE DATABASE [TEST2DS];
go
CREATE LOGIN [test_user] WITH PASSWORD=N'super_secret', DEFAULT_DATABASE=TEST2DS, CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO
USE [TEST2DS]
GO
CREATE USER [test_user] FOR LOGIN [test_user];
GO
GRANT control ON SCHEMA :: [dbo] TO test_user;
GO
-- Spring won't create tables even if ddl-auto = create
CREATE TABLE [MOVIES] (  
    [movie_id]   INT           IDENTITY (1, 1) NOT NULL,  
    [movie_title] NVARCHAR (40) NOT NULL,  
    [movie_description]  NVARCHAR (40) NOT NULL
);  
GO

-- undo things:
--drop user [test_user];
--drop login [test_user];
--use master;
--drop database [TEST2DS];
</pre>

<p>
This is for the MySQL side:
</p>
<pre>
CREATE DATABASE `actors_db`;
CREATE USER 'mysql_user'@'localhost' IDENTIFIED BY 'ultra_secret';
GRANT ALL ON actors_db.* to 'mysql_user' IDENTIFIED BY 'ultra_secret' WITH GRANT OPTION;
CREATE TABLE `ACTORS` (
  `actor_id` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `actor_name` text NOT NULL,
  `actor_comment` text NOT NULL
);
</pre>
