for connector in $(curl -s http://localhost:8083/connectors); do
  connector=$(echo $connector | sed 's/\[//g' | sed 's/\]//g' | sed 's/"//g' | sed 's/,/ /g')
  for conn in $connector; do
    echo "커넥터: $conn"
    curl -s http://localhost:8083/connectors/$conn/status | jq
    echo "------------------------"
  done
done