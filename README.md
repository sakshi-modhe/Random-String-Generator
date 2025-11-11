# Random String Generator App

An Android app that queries a random string from an existing content provider and displays it with metadata.  
Developed in **Kotlin** with a focus on clean architecture, error handling, and a smooth user experience.

---

## Features

- Set the **maximum length** of the string to be generated.
- Query the **IAV content provider** for a random string.
- Display each generated string along with:
  - The string value
  - Requested/generated length
  - Creation date and time
- Maintain a **list of all previously generated strings**.
- Delete **all strings** or **single strings** from the list.
- Gracefully handle **provider errors** and unreliable responses.

---

## Content Provider Details

- **Authority:** `com.iav.contestdataprovider`  
- **MIME Type:** `vnd.android.cursor.dir/text`  
- **Data URI:** `content://com.iav.contestdataprovider/text`  
- **Permissions:**
  - Read: `com.iav.contestdataprovider.READ`
  - Write: `com.iav.contestdataprovider.WRITE`  
- **Limit Parameter:** `ContentResolver.QUERY_ARG_LIMIT`  
- Returns JSON in the following format:
  ```json
  {
    "randomText": {
      "value": "oiahsfdoaoufbou9s",
      "length": 19,
      "created": "2024-10-02T07:38:49Z"
    }
  }
