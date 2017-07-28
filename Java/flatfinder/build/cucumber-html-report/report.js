$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("Authorization.feature");
formatter.feature({
  "line": 1,
  "name": "web authorization",
  "description": "",
  "id": "web-authorization",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 4,
  "name": "authentication",
  "description": "",
  "id": "web-authorization;authentication",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 3,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "I am a \u003cROLE\u003e with username \u003cUSR\u003e and password \u003cPWD\u003e",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I access \u003cURL\u003e",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "My authentication is \u003cisAuth\u003e with role \u003cROLE\u003e",
  "keyword": "Then "
});
formatter.examples({
  "line": 8,
  "name": "",
  "description": "",
  "id": "web-authorization;authentication;",
  "rows": [
    {
      "cells": [
        "URL",
        "USR",
        "PWD",
        "ROLE",
        "isAuth"
      ],
      "line": 9,
      "id": "web-authorization;authentication;;1"
    },
    {
      "cells": [
        "\"/login-form\"",
        "\"Alice\"",
        "\"password\"",
        "\"SEARCHER\"",
        "true"
      ],
      "line": 10,
      "id": "web-authorization;authentication;;2"
    },
    {
      "cells": [
        "\"/login-form\"",
        "\"Alice\"",
        "\"invalid\"",
        "\"SEARCHER\"",
        "false"
      ],
      "line": 11,
      "id": "web-authorization;authentication;;3"
    },
    {
      "cells": [
        "\"/login-form\"",
        "\"Bob\"",
        "\"admin\"",
        "\"ADMIN\"",
        "true"
      ],
      "line": 12,
      "id": "web-authorization;authentication;;4"
    },
    {
      "cells": [
        "\"/login-form\"",
        "\"Bob\"",
        "\"invalid\"",
        "\"ADMIN\"",
        "false"
      ],
      "line": 13,
      "id": "web-authorization;authentication;;5"
    },
    {
      "cells": [
        "\"/login-form\"",
        "\"invalid\"",
        "\"invalid\"",
        "\"LANDLORD\"",
        "false"
      ],
      "line": 14,
      "id": "web-authorization;authentication;;6"
    },
    {
      "cells": [
        "\"/login-form\"",
        "\"John\"",
        "\"password\"",
        "\"LANDLORD\"",
        "true"
      ],
      "line": 15,
      "id": "web-authorization;authentication;;7"
    },
    {
      "cells": [
        "\"/login-form\"",
        "\"John\"",
        "\"invalid\"",
        "\"LANDLORD\"",
        "false"
      ],
      "line": 16,
      "id": "web-authorization;authentication;;8"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 263037766,
  "status": "passed"
});
formatter.scenario({
  "line": 10,
  "name": "authentication",
  "description": "",
  "id": "web-authorization;authentication;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 3,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "I am a \"SEARCHER\" with username \"Alice\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I access \"/login-form\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "My authentication is true with role \"SEARCHER\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 8
    },
    {
      "val": "Alice",
      "offset": 33
    },
    {
      "val": "password",
      "offset": 54
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 182583827,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/login-form",
      "offset": 10
    }
  ],
  "location": "AuthorizationStepDefs.i_access(String)"
});
formatter.result({
  "duration": 17301581,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 37
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_true_with_role(String)"
});
formatter.result({
  "duration": 2945101,
  "status": "passed"
});
formatter.after({
  "duration": 355249354,
  "status": "passed"
});
formatter.before({
  "duration": 23944125,
  "status": "passed"
});
formatter.scenario({
  "line": 11,
  "name": "authentication",
  "description": "",
  "id": "web-authorization;authentication;;3",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 3,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "I am a \"SEARCHER\" with username \"Alice\" and password \"invalid\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I access \"/login-form\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "My authentication is false with role \"SEARCHER\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 8
    },
    {
      "val": "Alice",
      "offset": 33
    },
    {
      "val": "invalid",
      "offset": 54
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 134314,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/login-form",
      "offset": 10
    }
  ],
  "location": "AuthorizationStepDefs.i_access(String)"
});
formatter.result({
  "duration": 764388,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 38
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_false_with_role(String)"
});
formatter.result({
  "duration": 1951799,
  "status": "passed"
});
formatter.after({
  "duration": 126997705,
  "status": "passed"
});
formatter.before({
  "duration": 18201176,
  "status": "passed"
});
formatter.scenario({
  "line": 12,
  "name": "authentication",
  "description": "",
  "id": "web-authorization;authentication;;4",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 3,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "I am a \"ADMIN\" with username \"Bob\" and password \"admin\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I access \"/login-form\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "My authentication is true with role \"ADMIN\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 8
    },
    {
      "val": "Bob",
      "offset": 30
    },
    {
      "val": "admin",
      "offset": 49
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 128960,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/login-form",
      "offset": 10
    }
  ],
  "location": "AuthorizationStepDefs.i_access(String)"
});
formatter.result({
  "duration": 693884,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 37
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_true_with_role(String)"
});
formatter.result({
  "duration": 219544,
  "status": "passed"
});
formatter.after({
  "duration": 136887447,
  "status": "passed"
});
formatter.before({
  "duration": 19160119,
  "status": "passed"
});
formatter.scenario({
  "line": 13,
  "name": "authentication",
  "description": "",
  "id": "web-authorization;authentication;;5",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 3,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "I am a \"ADMIN\" with username \"Bob\" and password \"invalid\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I access \"/login-form\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "My authentication is false with role \"ADMIN\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 8
    },
    {
      "val": "Bob",
      "offset": 30
    },
    {
      "val": "invalid",
      "offset": 49
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 136546,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/login-form",
      "offset": 10
    }
  ],
  "location": "AuthorizationStepDefs.i_access(String)"
});
formatter.result({
  "duration": 721996,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 38
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_false_with_role(String)"
});
formatter.result({
  "duration": 103971,
  "status": "passed"
});
formatter.after({
  "duration": 112187412,
  "status": "passed"
});
formatter.before({
  "duration": 14589411,
  "status": "passed"
});
formatter.scenario({
  "line": 14,
  "name": "authentication",
  "description": "",
  "id": "web-authorization;authentication;;6",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 3,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "I am a \"LANDLORD\" with username \"invalid\" and password \"invalid\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I access \"/login-form\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "My authentication is false with role \"LANDLORD\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 8
    },
    {
      "val": "invalid",
      "offset": 33
    },
    {
      "val": "invalid",
      "offset": 56
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 139223,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/login-form",
      "offset": 10
    }
  ],
  "location": "AuthorizationStepDefs.i_access(String)"
});
formatter.result({
  "duration": 659524,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 38
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_false_with_role(String)"
});
formatter.result({
  "duration": 83891,
  "status": "passed"
});
formatter.after({
  "duration": 141658958,
  "status": "passed"
});
formatter.before({
  "duration": 17510416,
  "status": "passed"
});
formatter.scenario({
  "line": 15,
  "name": "authentication",
  "description": "",
  "id": "web-authorization;authentication;;7",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 3,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "I am a \"LANDLORD\" with username \"John\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I access \"/login-form\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "My authentication is true with role \"LANDLORD\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 8
    },
    {
      "val": "John",
      "offset": 33
    },
    {
      "val": "password",
      "offset": 53
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 91923,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/login-form",
      "offset": 10
    }
  ],
  "location": "AuthorizationStepDefs.i_access(String)"
});
formatter.result({
  "duration": 510484,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 37
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_true_with_role(String)"
});
formatter.result({
  "duration": 194555,
  "status": "passed"
});
formatter.after({
  "duration": 142314020,
  "status": "passed"
});
formatter.before({
  "duration": 14278836,
  "status": "passed"
});
formatter.scenario({
  "line": 16,
  "name": "authentication",
  "description": "",
  "id": "web-authorization;authentication;;8",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 3,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "I am a \"LANDLORD\" with username \"John\" and password \"invalid\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I access \"/login-form\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "My authentication is false with role \"LANDLORD\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 8
    },
    {
      "val": "John",
      "offset": 33
    },
    {
      "val": "invalid",
      "offset": 53
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 97724,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/login-form",
      "offset": 10
    }
  ],
  "location": "AuthorizationStepDefs.i_access(String)"
});
formatter.result({
  "duration": 633197,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 38
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_false_with_role(String)"
});
formatter.result({
  "duration": 80321,
  "status": "passed"
});
formatter.after({
  "duration": 99446277,
  "status": "passed"
});
formatter.before({
  "duration": 16509528,
  "status": "passed"
});
formatter.scenario({
  "line": 20,
  "name": "secure password",
  "description": "",
  "id": "web-authorization;secure-password",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 19,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 21,
  "name": "a developer \"Alice\"",
  "keyword": "Given "
});
formatter.step({
  "line": 22,
  "name": "I retrieve the password from the user credentials stored in the repository",
  "keyword": "When "
});
formatter.step({
  "line": 23,
  "name": "I should get the encrypted password \"password\"",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "Alice",
      "offset": 13
    }
  ],
  "location": "AuthorizationStepDefs.a_developer(String)"
});
formatter.result({
  "duration": 30754896,
  "status": "passed"
});
formatter.match({
  "location": "AuthorizationStepDefs.i_retrieve_the_password_from_the_user_credentials_stored_in_the_repository()"
});
formatter.result({
  "duration": 5232465,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "password",
      "offset": 37
    }
  ],
  "location": "AuthorizationStepDefs.i_should_get_the_encrypted_password(String)"
});
formatter.result({
  "duration": 100722934,
  "status": "passed"
});
formatter.after({
  "duration": 126265000,
  "status": "passed"
});
formatter.scenarioOutline({
  "line": 26,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \u003cROLES\u003e role with username \u003cUSER\u003e and password \u003cPW\u003e",
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \u003cService\u003e",
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then \u003cisAuthorized\u003e with role \u003cROLES\u003e",
  "keyword": "Then "
});
formatter.examples({
  "line": 30,
  "name": "",
  "description": "",
  "id": "web-authorization;authorization;",
  "rows": [
    {
      "cells": [
        "Service",
        "USER",
        "PW",
        "ROLES",
        "isAuthorized"
      ],
      "line": 31,
      "id": "web-authorization;authorization;;1"
    },
    {
      "cells": [
        "\"/admin-home\"",
        "\"Alice\"",
        "\"password\"",
        "\"SEARCHER\"",
        "false"
      ],
      "line": 32,
      "id": "web-authorization;authorization;;2"
    },
    {
      "cells": [
        "\"/admin-home\"",
        "\"Bob\"",
        "\"admin\"",
        "\"ADMIN\"",
        "true"
      ],
      "line": 33,
      "id": "web-authorization;authorization;;3"
    },
    {
      "cells": [
        "\"/admin-home\"",
        "\"John\"",
        "\"password\"",
        "\"LANDLORD\"",
        "false"
      ],
      "line": 34,
      "id": "web-authorization;authorization;;4"
    },
    {
      "cells": [
        "\"/user-account\"",
        "\"Alice\"",
        "\"password\"",
        "\"SEARCHER\"",
        "true"
      ],
      "line": 35,
      "id": "web-authorization;authorization;;5"
    },
    {
      "cells": [
        "\"/user-account\"",
        "\"Bob\"",
        "\"admin\"",
        "\"ADMIN\"",
        "true"
      ],
      "line": 36,
      "id": "web-authorization;authorization;;6"
    },
    {
      "cells": [
        "\"/user-account\"",
        "\"John\"",
        "\"password\"",
        "\"LANDLORD\"",
        "true"
      ],
      "line": 37,
      "id": "web-authorization;authorization;;7"
    },
    {
      "cells": [
        "\"/report-express-interest\"",
        "\"Alice\"",
        "\"password\"",
        "\"SEARCHER\"",
        "true"
      ],
      "line": 38,
      "id": "web-authorization;authorization;;8"
    },
    {
      "cells": [
        "\"/report-express-interest\"",
        "\"Bob\"",
        "\"admin\"",
        "\"ADMIN\"",
        "true"
      ],
      "line": 39,
      "id": "web-authorization;authorization;;9"
    },
    {
      "cells": [
        "\"/report-express-interest\"",
        "\"John\"",
        "\"password\"",
        "\"LANDLORD\"",
        "true"
      ],
      "line": 40,
      "id": "web-authorization;authorization;;10"
    },
    {
      "cells": [
        "\"/buddy-up\"",
        "\"Alice\"",
        "\"password\"",
        "\"SEARCHER\"",
        "true"
      ],
      "line": 41,
      "id": "web-authorization;authorization;;11"
    },
    {
      "cells": [
        "\"/buddy-up\"",
        "\"Bob\"",
        "\"admin\"",
        "\"ADMIN\"",
        "false"
      ],
      "line": 42,
      "id": "web-authorization;authorization;;12"
    },
    {
      "cells": [
        "\"/buddy-up\"",
        "\"John\"",
        "\"password\"",
        "\"LANDLORD\"",
        "false"
      ],
      "line": 43,
      "id": "web-authorization;authorization;;13"
    },
    {
      "cells": [
        "\"/file-report\"",
        "\"Alice\"",
        "\"password\"",
        "\"SEARCHER\"",
        "true"
      ],
      "line": 44,
      "id": "web-authorization;authorization;;14"
    },
    {
      "cells": [
        "\"/file-report\"",
        "\"Bob\"",
        "\"admin\"",
        "\"ADMIN\"",
        "false"
      ],
      "line": 45,
      "id": "web-authorization;authorization;;15"
    },
    {
      "cells": [
        "\"/file-report\"",
        "\"John\"",
        "\"password\"",
        "\"LANDLORD\"",
        "true"
      ],
      "line": 46,
      "id": "web-authorization;authorization;;16"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 15347997,
  "status": "passed"
});
formatter.scenario({
  "line": 32,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"SEARCHER\" role with username \"Alice\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/admin-home\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then false with role \"SEARCHER\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 8
    },
    {
      "val": "Alice",
      "offset": 38
    },
    {
      "val": "password",
      "offset": 59
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 98170,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/admin-home",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 672465,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 43
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_false_with_role(String)"
});
formatter.result({
  "duration": 3438630,
  "status": "passed"
});
formatter.after({
  "duration": 112870586,
  "status": "passed"
});
formatter.before({
  "duration": 14036088,
  "status": "passed"
});
formatter.scenario({
  "line": 33,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;3",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"ADMIN\" role with username \"Bob\" and password \"admin\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/admin-home\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then true with role \"ADMIN\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 8
    },
    {
      "val": "Bob",
      "offset": 35
    },
    {
      "val": "admin",
      "offset": 54
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 156626,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/admin-home",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 695222,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 42
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_true_with_role(String)"
});
formatter.result({
  "duration": 225791,
  "status": "passed"
});
formatter.after({
  "duration": 111427487,
  "status": "passed"
});
formatter.before({
  "duration": 16045897,
  "status": "passed"
});
formatter.scenario({
  "line": 34,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;4",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"LANDLORD\" role with username \"John\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/admin-home\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then false with role \"LANDLORD\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 8
    },
    {
      "val": "John",
      "offset": 38
    },
    {
      "val": "password",
      "offset": 58
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 95046,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/admin-home",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 466754,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 43
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_false_with_role(String)"
});
formatter.result({
  "duration": 91031,
  "status": "passed"
});
formatter.after({
  "duration": 145614319,
  "status": "passed"
});
formatter.before({
  "duration": 15909352,
  "status": "passed"
});
formatter.scenario({
  "line": 35,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;5",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"SEARCHER\" role with username \"Alice\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/user-account\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then true with role \"SEARCHER\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 8
    },
    {
      "val": "Alice",
      "offset": 38
    },
    {
      "val": "password",
      "offset": 59
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 124497,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/user-account",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 766619,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 42
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_true_with_role(String)"
});
formatter.result({
  "duration": 143239,
  "status": "passed"
});
formatter.after({
  "duration": 142490280,
  "status": "passed"
});
formatter.before({
  "duration": 13663934,
  "status": "passed"
});
formatter.scenario({
  "line": 36,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;6",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"ADMIN\" role with username \"Bob\" and password \"admin\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/user-account\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then true with role \"ADMIN\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 8
    },
    {
      "val": "Bob",
      "offset": 35
    },
    {
      "val": "admin",
      "offset": 54
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 142347,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/user-account",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 473894,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 42
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_true_with_role(String)"
});
formatter.result({
  "duration": 136099,
  "status": "passed"
});
formatter.after({
  "duration": 123910702,
  "status": "passed"
});
formatter.before({
  "duration": 15563972,
  "status": "passed"
});
formatter.scenario({
  "line": 37,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;7",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"LANDLORD\" role with username \"John\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/user-account\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then true with role \"LANDLORD\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 8
    },
    {
      "val": "John",
      "offset": 38
    },
    {
      "val": "password",
      "offset": 58
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 172690,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/user-account",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 504237,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 42
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_true_with_role(String)"
});
formatter.result({
  "duration": 190540,
  "status": "passed"
});
formatter.after({
  "duration": 88112096,
  "status": "passed"
});
formatter.before({
  "duration": 13579598,
  "status": "passed"
});
formatter.scenario({
  "line": 38,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;8",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"SEARCHER\" role with username \"Alice\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/report-express-interest\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then true with role \"SEARCHER\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 8
    },
    {
      "val": "Alice",
      "offset": 38
    },
    {
      "val": "password",
      "offset": 59
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 136992,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/report-express-interest",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 595268,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 42
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_true_with_role(String)"
});
formatter.result({
  "duration": 160196,
  "status": "passed"
});
formatter.after({
  "duration": 100722934,
  "status": "passed"
});
formatter.before({
  "duration": 16022694,
  "status": "passed"
});
formatter.scenario({
  "line": 39,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;9",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"ADMIN\" role with username \"Bob\" and password \"admin\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/report-express-interest\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then true with role \"ADMIN\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 8
    },
    {
      "val": "Bob",
      "offset": 35
    },
    {
      "val": "admin",
      "offset": 54
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 125390,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/report-express-interest",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 617579,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 42
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_true_with_role(String)"
});
formatter.result({
  "duration": 191432,
  "status": "passed"
});
formatter.after({
  "duration": 109768859,
  "status": "passed"
});
formatter.before({
  "duration": 13207890,
  "status": "passed"
});
formatter.scenario({
  "line": 40,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;10",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"LANDLORD\" role with username \"John\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/report-express-interest\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then true with role \"LANDLORD\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 8
    },
    {
      "val": "John",
      "offset": 38
    },
    {
      "val": "password",
      "offset": 58
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 88353,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/report-express-interest",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 439980,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 42
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_true_with_role(String)"
});
formatter.result({
  "duration": 104863,
  "status": "passed"
});
formatter.after({
  "duration": 103759512,
  "status": "passed"
});
formatter.before({
  "duration": 12382815,
  "status": "passed"
});
formatter.scenario({
  "line": 41,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;11",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"SEARCHER\" role with username \"Alice\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/buddy-up\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then true with role \"SEARCHER\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 8
    },
    {
      "val": "Alice",
      "offset": 38
    },
    {
      "val": "password",
      "offset": 59
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 85675,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/buddy-up",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 457830,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 42
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_true_with_role(String)"
});
formatter.result({
  "duration": 109326,
  "status": "passed"
});
formatter.after({
  "duration": 209670733,
  "status": "passed"
});
formatter.before({
  "duration": 14908909,
  "status": "passed"
});
formatter.scenario({
  "line": 42,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;12",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"ADMIN\" role with username \"Bob\" and password \"admin\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/buddy-up\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then false with role \"ADMIN\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 8
    },
    {
      "val": "Bob",
      "offset": 35
    },
    {
      "val": "admin",
      "offset": 54
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 107095,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/buddy-up",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 438642,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 43
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_false_with_role(String)"
});
formatter.result({
  "duration": 53547,
  "status": "passed"
});
formatter.after({
  "duration": 103858575,
  "status": "passed"
});
formatter.before({
  "duration": 12365859,
  "status": "passed"
});
formatter.scenario({
  "line": 43,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;13",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"LANDLORD\" role with username \"John\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/buddy-up\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then false with role \"LANDLORD\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 8
    },
    {
      "val": "John",
      "offset": 38
    },
    {
      "val": "password",
      "offset": 58
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 89245,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/buddy-up",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 455152,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 43
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_false_with_role(String)"
});
formatter.result({
  "duration": 58010,
  "status": "passed"
});
formatter.after({
  "duration": 126511317,
  "status": "passed"
});
formatter.before({
  "duration": 11938373,
  "status": "passed"
});
formatter.scenario({
  "line": 44,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;14",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"SEARCHER\" role with username \"Alice\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/file-report\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then true with role \"SEARCHER\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 8
    },
    {
      "val": "Alice",
      "offset": 38
    },
    {
      "val": "password",
      "offset": 59
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 85675,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/file-report",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 602854,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "SEARCHER",
      "offset": 42
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_true_with_role(String)"
});
formatter.result({
  "duration": 149486,
  "status": "passed"
});
formatter.after({
  "duration": 93197752,
  "status": "passed"
});
formatter.before({
  "duration": 12759877,
  "status": "passed"
});
formatter.scenario({
  "line": 45,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;15",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"ADMIN\" role with username \"Bob\" and password \"admin\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/file-report\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then false with role \"ADMIN\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 8
    },
    {
      "val": "Bob",
      "offset": 35
    },
    {
      "val": "admin",
      "offset": 54
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 114680,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/file-report",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 493082,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "ADMIN",
      "offset": 43
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_false_with_role(String)"
});
formatter.result({
  "duration": 55778,
  "status": "passed"
});
formatter.after({
  "duration": 131465782,
  "status": "passed"
});
formatter.before({
  "duration": 33025302,
  "status": "passed"
});
formatter.scenario({
  "line": 46,
  "name": "authorization",
  "description": "",
  "id": "web-authorization;authorization;;16",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 25,
      "name": "@security"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "I am a \"LANDLORD\" role with username \"John\" and password \"password\"",
  "matchedColumns": [
    1,
    2,
    3
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "I access a \"/file-report\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "My authentication is then true with role \"LANDLORD\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 8
    },
    {
      "val": "John",
      "offset": 38
    },
    {
      "val": "password",
      "offset": 58
    }
  ],
  "location": "AuthorizationStepDefs.i_am_a_role_with_username_and_password(String,String,String)"
});
formatter.result({
  "duration": 127621,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "/file-report",
      "offset": 12
    }
  ],
  "location": "AuthorizationStepDefs.i_access_a(String)"
});
formatter.result({
  "duration": 635428,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "LANDLORD",
      "offset": 42
    }
  ],
  "location": "AuthorizationStepDefs.my_authentication_is_then_true_with_role(String)"
});
formatter.result({
  "duration": 202588,
  "status": "passed"
});
formatter.after({
  "duration": 93220955,
  "status": "passed"
});
formatter.uri("Feedback.feature");
formatter.feature({
  "line": 1,
  "name": "Feedback on properties",
  "description": "",
  "id": "feedback-on-properties",
  "keyword": "Feature"
});
formatter.before({
  "duration": 12571570,
  "status": "passed"
});
formatter.background({
  "line": 3,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 4,
  "name": "an searcher named \u003csname\u003e",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "has selected \u003cpid\u003e on the property page",
  "keyword": "And "
});
formatter.step({
  "line": 6,
  "name": "an admin approves the rating request",
  "keyword": "And "
});
formatter.match({
  "location": "FeedbackStepDefs.an_searcher_named_sname()"
});
formatter.result({
  "duration": 4491727,
  "error_message": "cucumber.api.PendingException: TODO: implement me\r\n\tat flatfinder.FeedbackStepDefs.an_searcher_named_sname(FeedbackStepDefs.java:58)\r\n\tat ✽.Given an searcher named \u003csname\u003e(Feedback.feature:4)\r\n",
  "status": "pending"
});
formatter.match({
  "location": "FeedbackStepDefs.has_selected_pid_on_the_property_page()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "FeedbackStepDefs.an_admin_approves_the_rating_request()"
});
formatter.result({
  "status": "skipped"
});
formatter.scenario({
  "line": 8,
  "name": "Searcher \u003csname\u003e rates a property",
  "description": "",
  "id": "feedback-on-properties;searcher-\u003csname\u003e-rates-a-property",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 9,
  "name": "Im logged in as \u003csname\u003e",
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "want to rate property \u003cpid\u003e",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "submitting the rating \"6\"",
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "the rating request is sent to the admin for approval",
  "keyword": "Then "
});
formatter.match({
  "location": "FeedbackStepDefs.im_logged_in_as_sname()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "FeedbackStepDefs.want_to_rate_property_pid()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "6",
      "offset": 23
    }
  ],
  "location": "FeedbackStepDefs.submitting_the_rating(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "FeedbackStepDefs.the_rating_request_is_sent_to_the_admin_for_approval()"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "duration": 165275553,
  "status": "passed"
});
formatter.before({
  "duration": 16620192,
  "status": "passed"
});
formatter.background({
  "line": 3,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 4,
  "name": "an searcher named \u003csname\u003e",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "has selected \u003cpid\u003e on the property page",
  "keyword": "And "
});
formatter.step({
  "line": 6,
  "name": "an admin approves the rating request",
  "keyword": "And "
});
formatter.match({
  "location": "FeedbackStepDefs.an_searcher_named_sname()"
});
formatter.result({
  "duration": 421685,
  "error_message": "cucumber.api.PendingException: TODO: implement me\r\n\tat flatfinder.FeedbackStepDefs.an_searcher_named_sname(FeedbackStepDefs.java:58)\r\n\tat ✽.Given an searcher named \u003csname\u003e(Feedback.feature:4)\r\n",
  "status": "pending"
});
formatter.match({
  "location": "FeedbackStepDefs.has_selected_pid_on_the_property_page()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "FeedbackStepDefs.an_admin_approves_the_rating_request()"
});
formatter.result({
  "status": "skipped"
});
formatter.scenario({
  "line": 14,
  "name": "Admin approves \u003csname\u003e rating request",
  "description": "",
  "id": "feedback-on-properties;admin-approves-\u003csname\u003e-rating-request",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 15,
  "name": "Im logged in as admin",
  "keyword": "Given "
});
formatter.step({
  "line": 16,
  "name": "want to approve a rating request",
  "keyword": "And "
});
formatter.step({
  "line": 17,
  "name": "clicking the approve on the request",
  "keyword": "When "
});
formatter.step({
  "line": 18,
  "name": "the rating should appear on property page \u003cpid\u003e",
  "keyword": "Then "
});
formatter.match({
  "location": "FeedbackStepDefs.im_logged_in_as_admin()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "FeedbackStepDefs.want_to_approve_a_rating_request()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "FeedbackStepDefs.clicking_the_approve_on_the_request()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "FeedbackStepDefs.the_rating_should_appear_on_property_page_pid()"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "duration": 130970022,
  "status": "passed"
});
formatter.uri("Registration.feature");
formatter.feature({
  "line": 1,
  "name": "Register as a member",
  "description": "As a site visitor\r\nI want to join the website\r\nIn order to find property to rent",
  "id": "register-as-a-member",
  "keyword": "Feature"
});
formatter.before({
  "duration": 11771929,
  "status": "passed"
});
formatter.scenario({
  "line": 7,
  "name": "open registration page",
  "description": "",
  "id": "register-as-a-member;open-registration-page",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 6,
      "name": "@controller"
    }
  ]
});
formatter.step({
  "line": 8,
  "name": "im on the flatfinder home page",
  "keyword": "Given "
});
formatter.step({
  "line": 9,
  "name": "the registration link is clicked",
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "the registration page should open",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.after({
  "duration": 115441750,
  "status": "passed"
});
formatter.before({
  "duration": 12790667,
  "status": "passed"
});
formatter.scenario({
  "line": 13,
  "name": "register as any role",
  "description": "",
  "id": "register-as-a-member;register-as-any-role",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 12,
      "name": "@domain"
    }
  ]
});
formatter.step({
  "line": 14,
  "name": "im completing the \"Register\" form",
  "keyword": "Given "
});
formatter.step({
  "line": 15,
  "name": "the form is completed with",
  "rows": [
    {
      "cells": [
        "FIRSTNAME",
        "LASTNAME",
        "ADDRESS1",
        "ADDRESS2",
        "ADDRESS3",
        "POSTCODE",
        "PHONE",
        "ROLE",
        "USERNAME",
        "PASSWORD"
      ],
      "line": 16
    },
    {
      "cells": [
        "\"Alice\"",
        "\"Smith\"",
        "\"86\"",
        "\"West Road\"",
        "\"Leicester\"",
        "\"LE2 7TG\"",
        "\"0778542125\"",
        "\"searcher\"",
        "\"alice\"",
        "\"password\""
      ],
      "line": 17
    },
    {
      "cells": [
        "\"John\"",
        "\"Wick\"",
        "\"22a\"",
        "\"North Street\"",
        "\"Leicester\"",
        "\"LE1 1NY\"",
        "\"0116225124\"",
        "\"landlord\"",
        "\"john\"",
        "\"password\""
      ],
      "line": 18
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 19,
  "name": "the Register form is submitted",
  "keyword": "When "
});
formatter.step({
  "line": 20,
  "name": "I should see that \u003cFIRSTNAME\u003e with \u003cPOSTCODE\u003e and \u003cROLE\u003e has been registered",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.after({
  "duration": 158599988,
  "status": "passed"
});
});