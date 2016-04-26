USE achievmentRewardsDB;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECTPROPERTY(object_id('dbo.setAchievementRedeemed'), N'IsProcedure') = 1
	DROP PROCEDURE [dbo].[setAchievementRedeemed]
GO

CREATE PROCEDURE setAchievementRedeemed
	@uaID INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	UPDATE	tbl_userAchievements
	SET		RedeemedAt=dateadd(hour, -4, GETDATE())
	WHERE	Id=@uaID

	SELECT	ua.RedeemedAt
	FROM	tbl_userAchievements ua
	WHERE	ua.Id = @uaID

END
GO
