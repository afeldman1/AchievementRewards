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
IF OBJECTPROPERTY(object_id('dbo.getUserAchievements'), N'IsProcedure') = 1
DROP PROCEDURE [dbo].[getUserAchievements]
GO

CREATE PROCEDURE getUserAchievements
	@currId BIGINT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT		a.Name,
				a.Description,
				m.LogoUrl
	FROM		tbl_Achievements a
	RIGHT JOIN	tbl_userAchievements ua
	ON			a.id = ua.AchievementId
	LEFT JOIN	tbl_Merchants m
	ON			m.Id = a.MerchantId
	Where		ua.UserId = @currId

END
GO

