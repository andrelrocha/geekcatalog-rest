UPDATE socialapp.games
SET yr_of_release =
    CASE
        WHEN id = 'f2c40adc-64c8-4cac-8547-5e5778d04166' THEN 2014
        WHEN id = 'e9189719-62ac-44cd-8e69-07666e1316fc' THEN 2009
        WHEN id = '9924aeb8-2457-4a06-9d94-49b1cc1bdff9' THEN 2018
        WHEN id = '62652bfd-bdbf-49e5-a222-2b4060c99c3a' THEN 2007
        WHEN id = '7dd2f614-8e7c-44c5-8df8-635b9f6a83ee' THEN 2019
        WHEN id = 'd3d8a7c2-9951-4ff2-a8c9-8f7faf5a48cc' THEN 2023
        WHEN id = '91c23ab2-a57e-42b7-8cfc-76f0bfc6bd34' THEN 1995
        WHEN id = 'be301609-1247-430e-99bd-20d1cd6da7c6' THEN 2006
        WHEN id = '149f9e5b-6b83-428a-af27-48f5eebc8c3a' THEN 2016
        WHEN id = 'd0b00ed1-819e-438d-ac35-93ed842fb519' THEN 2008
        WHEN id = '524ce203-8b24-41f3-a7ef-ca3d7c169ef0' THEN 1994
        WHEN id = '8e05f89b-f03f-4497-931f-1cf49c368276' THEN 1993
        WHEN id = '1246d567-4a0d-445a-a1f7-026eaedf760c' THEN 2022
        WHEN id = '4bc6dae9-9a9c-4187-b21e-52e339fed6ac' THEN 2012
        WHEN id = 'ec08b6ac-fe5c-4dc9-9a9e-03a2a3347dd2' THEN 2013
        WHEN id = '666b8411-a2f4-497a-891d-ae6c5b03ddb3' THEN 2016
        WHEN id = '22f324c1-1fe4-44bd-a856-5c40cef6d455' THEN 2021
        WHEN id = '81a1badc-7cdb-43d2-8369-73067dc965d0' THEN 2005
        WHEN id = '86830fe7-d964-4ac3-85ae-90b4123c765c' THEN 2013
        WHEN id = '3192b7e7-b296-4c38-a28e-c90d18690ffe' THEN 2016
        WHEN id = '1366845e-34e6-4a00-a448-08e5a3030956' THEN 2014
        WHEN id = '11620413-a49b-477c-bd9e-1791f973aa2b' THEN 2018
        WHEN id = 'feed4ddf-a057-406d-a474-fb6a996c47ce' THEN 2007
        WHEN id = '8529b648-d249-4393-b780-de10da37dccc' THEN 2012
        WHEN id = '64039ebd-e024-443f-9918-765dd9241691' THEN 2008
        WHEN id = '55e162ee-c34e-47c8-be8b-c814034ec2d4' THEN 2020
        WHEN id = '55713689-f673-461f-ba20-157a6a2e88b1' THEN 2004
        WHEN id = 'af35182c-1283-43e8-8127-d2be132fa80c' THEN 2009
        WHEN id = 'a052547e-3213-46ba-bc4c-75ac42da6cd6' THEN 2010
        WHEN id = 'd2a0b315-eaaf-47e9-86a0-b498d71f1afb' THEN 2010
        WHEN id = '493d0d17-64fa-4952-8566-1ed1365d1cc0' THEN 2013
        WHEN id = 'b3b8281d-4cb0-48ee-bd5d-b89a623dd138' THEN 2022
        WHEN id = 'ff0e21b2-c3c2-41d9-973c-7b98d1b1dad0' THEN 2012
        WHEN id = '4a12b149-2331-497f-bd6d-a99aa15a67d5' THEN 2019
        WHEN id = '333ba5a7-8fdd-488f-bc48-59b1975da8a7' THEN 2018
        WHEN id = 'c287adfa-976a-47e0-80c5-d4072b8a9aa3' THEN 2015
        WHEN id = 'cfac3ae1-2d7a-4904-b916-39311e9e290e' THEN 2015
    END;