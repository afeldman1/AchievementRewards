USE achievmentRewardsDB;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
IF OBJECTPROPERTY(object_id('dbo.getUser'), N'IsProcedure') = 1
DROP PROCEDURE [dbo].[getUser]
GO


CREATE PROCEDURE getUser
	@fbID BIGINT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT	FirstName,
			LastName,
			Gender,
			Birthday
	FROM	tbl_Users
	WHERE	FBid = @fbID
END
GO

