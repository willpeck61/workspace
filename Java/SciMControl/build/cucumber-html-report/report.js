$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("AssessChangeRequest.feature");
formatter.feature({
  "line": 1,
  "name": "Assess change request",
  "description": "In order to either approve or reject change requests\nAs a project manager\nI want to revise change requests",
  "id": "assess-change-request",
  "keyword": "Feature"
});
formatter.uri("Authorization.feature");
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
        "\"DEVELOPER\"",
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
        "\"DEVELOPER\"",
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
        "\"MANAGER\"",
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
        "\"MANAGER\"",
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
        "\"MANAGER\"",
        "false"
      ],
      "line": 14,
      "id": "web-authorization;authentication;;6"
    }
  ],
  "keyword": "Examples"
});
formatter.uri("CompleteChangeRequest.feature");
formatter.feature({
  "line": 1,
  "name": "complete change request",
  "description": "As a developer \nI want to process the change requests allocated to me\nSo that we can make progress on project development",
  "id": "complete-change-request",
  "keyword": "Feature"
});
