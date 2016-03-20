USE achievmentRewardsDB;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECTPROPERTY(object_id('dbo.getAchievementDetails'), N'IsProcedure') = 1
DROP PROCEDURE [dbo].[getAchievementDetails]
GO

CREATE PROCEDURE getAchievementDetails
	@uaID INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT		ua.Id AS UserAchievementId,
				a.Name AS AchievementName,
				a.Description AS AchievementDescription,
				a.RewardName,
				a.RewardDescription,
				m.Name AS MerchantName,
				m.Description AS MerchantDescription,
				m.LogoUrl,
				ua.Progress,
				a.TrackingMax,
				ua.RedeemedAt
	FROM		tbl_userAchievements ua 
	LEFT JOIN	tbl_Achievements a ON ua.AchievementId = a.Id
	LEFT JOIN	tbl_Merchants m ON a.MerchantId = m.Id
	LEFT JOIN	tbl_MerchantLocs ml ON m.Id = ml.MerchantId
	WHERE		ua.Id = @uaID

END
GO