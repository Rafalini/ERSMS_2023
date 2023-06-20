# Image Analyzer

## How to run the service
### Through docker-compose:
1. Copy silken-forest-388009-9d4d8198cba5.json into ./src/main/resources directory.

### Locally:
1. Copy silken-forest-388009-9d4d8198cba5.json into ./src/main/resources directory.
2. Set GOOGLE_APPLICATION_CREDENTIALS environment variable to absolute path to silken-forest-388009-9d4d8198cba5.json
The easiest way to do that is to set it in Intellij:
   1. Go to Edit Configurations..
   2. Choose ImageAnalyzer
   3. Modify options -> Environment variables 
   4. In 'environment variables' write: GOOGLE_APPLICATION_CREDENTIALS=path/to/silken-forest-388009-9d4d8198cba5.json