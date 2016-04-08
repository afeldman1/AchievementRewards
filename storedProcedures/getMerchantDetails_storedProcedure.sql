USE achievmentRewardsDB;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECTPROPERTY(object_id('dbo.getMerchantDetails'), N'IsProcedure') = 1
DROP PROCEDURE [dbo].[getMerchantDetails]
GO

CREATE PROCEDURE getMerchantDetails
	@mID INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT		m.Description AS MerchantDescription
	FROM		tbl_Merchants m
	WHERE		m.Id = @mID

	SELECT		ml.Address,
				ml.Lat,
				ml.Long,
				ml.PhoneNum,
				ml.Rad
	FROM		tbl_Merchants m
	LEFT JOIN	tbl_MerchantLocs ml ON ml.MerchantId = m.Id
	WHERE		m.Id = @mID

	SELECT		a.Id AS AchievementId,
	            a.Name AS AchievementName
	FROM		tbl_Merchants m
	LEFT JOIN	tbl_Achievements a ON a.MerchantId = m.Id
	WHERE		m.Id = @mID
				AND a.isVisible = 1

END
GO