USE achievmentRewardsDB;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECTPROPERTY(object_id('dbo.getUserAchievements'), N'IsProcedure') = 1
DROP PROCEDURE [dbo].[getUserAchievements]
GO

CREATE PROCEDURE getUserAchievements
	@currID BIGINT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT		ua.Id AS UserAchievementId,
				a.Name AS AchievementName,
				m.Name AS MerchantName,
				ua.Progress,
				a.TrackingMax,
				ua.RedeemedAt
	FROM		tbl_userAchievements ua
	LEFT JOIN	tbl_Achievements a ON ua.AchievementId = a.Id
	LEFT JOIN	tbl_Merchants m ON a.MerchantId = m.Id
	WHERE		ua.UserId = @currID

END
GO
