/*
Copyright (c) Microsoft
All Rights Reserved
Apache 2.0 License
 
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
     http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 
See the Apache Version 2.0 License for specific language governing permissions and limitations under the License.
 */

package emre.ax365test;

import com.microsoft.aad.adal.AuthenticationResult;

public class Constants {


    //Klasse für die Konstanten


    public static final String SDK_VERSION = "1.0";

    public static final String UTF8_ENCODING = "UTF-8";

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String HEADER_AUTHORIZATION_VALUE_PREFIX = "Bearer ";

    // -------------------------------AAD
    // PARAMETERS----------------------------------
    public static String AUTHORITY_URL = "https://login.windows.net/rdag365.net"; //AX LOGIN Screen
    public static String CLIENT_ID = "dcd8af56-90e2-49bc-ac73-d326d46a15a9";//CLIENT ID Von Azure
    public static String RESOURCE_ID = "https://rdagdmo01094f27c6aeba9322aos.cloudax.dynamics.com";//AX Adresse
    public static String REDIRECT_URL = "https://rdagdmo01094f27c6aeba9322aos.cloudax.dynamics.com/oauth";//Die im Azure angegebene Redirect url --> KANN irgendetwas mit http:// sein
    public static String CORRELATION_ID = "";
    public static String USER_HINT = "";
    public static String EXTRA_QP = "";
    public static boolean FULL_SCREEN = true;
    public static AuthenticationResult CURRENT_RESULT = null;
    private static String tmp;
    public static StringBuilder sb;


    public static void setSB(StringBuilder t) {

        sb = t;

    }


    public static String getTmp() {
        return tmp;
    }

    public static void setTmp(String tmp) {
        Constants.tmp = tmp;
    }
}
