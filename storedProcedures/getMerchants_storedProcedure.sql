USE achievmentRewardsDB;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECTPROPERTY(object_id('dbo.getMerchants'), N'IsProcedure') = 1
DROP PROCEDURE [dbo].[getMerchants]
GO

CREATE PROCEDURE getMerchants
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT		m.Id AS MerchantId,
				m.Name AS MerchantName,
				m.LogoUrl
	FROM		tbl_Merchants m

	/*
	SELECT		ml.Lat,
				ml.Long
	FROM		tbl_Merchants m
	LEFT JOIN	tbl_MerchantLocs ml ON ml.MerchantId = m.Id
	*/
END
GO