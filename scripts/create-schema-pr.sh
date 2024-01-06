#!/bin/bash

current_commit=$(git rev-parse HEAD)
cd schemas

if [ -n "$(git status -s)" ]; then
    git checkout -b update/catalog/$current_commit
    git commit -am "update catalog schema based on $current_commit"
    git push origin update/catalog/$current_commit
    gh pr create --base main --head update/catalog/$current_commit --title "Update catalog schema based on $current_commit" --body "Automated PR created by script."
    echo "Pull request created successfully."
else
    echo "No changes in the 'schemas' directory. Exiting without creating a pull request."
fi
