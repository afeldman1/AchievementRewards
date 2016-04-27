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
	@currID BIGINT,
	@getRedeemed BIT = NULL
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	IF @getRedeemed IS NOT NULL AND @getRedeemed = 1
	BEGIN
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
					AND ua.RedeemedAt IS NOT NULL
	END
	ELSE
	BEGIN
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
					AND ua.RedeemedAt IS NULL
	END
END
GO
