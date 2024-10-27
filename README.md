test developed by Keyur Chotaliya


1. tested given endpoints 'authenticate/GetUserAuthorizationToken' and 'Account/GetAccountList'
2. created User and CustomUserDetailsService for spring security,
3. BCrypt Password encoder and custom JWT token implementation.
4. included SwaggerConfig for API documentation generation.

5. A DataInitialiser setup for creating one default use with given creds if already not existed.

6. /fetchAndStoreData - validates request params and internally calls 'Account/GetAccountList' extracts down the necessary fields only 
   and maps response to vehical Objects for Data saving if already not existed. no duplicate entries are stored.
   Error scenarios of various http status - handled, null/Empty scenarios - handled

7. with /generateToken a custom JWT token is generated which will be used in /getData APi,
   Which finally return the stored vehicle's data.
   Error Scenarion - null/Empty - handled

8. by installing and running the project following swagger link should be published which contains API doumentation

9. Postman api collection is kept under src/main/resources/v1-collection along with sql-script file



