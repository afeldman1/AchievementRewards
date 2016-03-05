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
IF OBJECTPROPERTY(object_id('dbo.getUserInProgressAchievements'), N'IsProcedure') = 1
DROP PROCEDURE [dbo].[getUserInProgressAchievements]
GO

CREATE PROCEDURE getUserInProgressAchievements
	@fbID BIGINT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT		m.Name AS Merchant,
				a.Name AS Achievement,
				ua.Progress,
				a.TrackingMax
	FROM		tbl_userAchievements ua 
	LEFT JOIN	tbl_Achievements a ON ua.AchievementId = a.Id
	LEFT JOIN	tbl_Merchants m ON a.MerchantId = m.Id
	WHERE		ua.UserId = @fbID
				AND ua.Progress != a.TrackingMax
				AND ua.RedeemedAt IS NULL

END
GO

