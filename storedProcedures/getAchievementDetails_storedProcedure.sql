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
	@uaID INT = NULL,
	@aID INT = NULL
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

IF @uaID IS NOT NULL
BEGIN
	SELECT		a.Id AS AchievementId,
				a.Description AS AchievementDescription,
				a.RewardName,
				a.RewardDescription,
				m.Id AS MerchantId
	FROM		tbl_Achievements a 
	LEFT JOIN	tbl_userAchievements ua ON ua.AchievementId = a.Id
	LEFT JOIN	tbl_Merchants m ON a.MerchantId = m.Id
	WHERE		ua.Id = @uaID
END
ELSE
BEGIN
	SELECT		a.Description AS AchievementDescription,
				a.RewardName,
				a.RewardDescription,
				m.Id AS MerchantId
	FROM		tbl_Achievements a
	LEFT JOIN	tbl_Merchants m ON a.MerchantId = m.Id
	WHERE		a.Id = @aID
END

END
GO