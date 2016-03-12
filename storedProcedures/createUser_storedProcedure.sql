USE achievmentRewardsDB;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECTPROPERTY(object_id('dbo.createUser'), N'IsProcedure') = 1
DROP PROCEDURE [dbo].[createUser]
GO

CREATE PROCEDURE createUser
	@fbID BIGINT,
	@FirstName VARCHAR(255),
	@LastName VARCHAR(255),
	@Email VARCHAR(255),
	@Gender VARCHAR,
	@Birthday DATE
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	INSERT INTO	tbl_Users (FBid, FirstName, LastName, Email, Gender, Birthday)
	VALUES (@fbID, @FirstName, @LastName, @Email, @Gender, @Birthday);

END
GO

