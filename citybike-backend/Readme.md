Start with "mvn jetty:run"



###############
# Indexes #
###############

db.stations.ensureIndex({"position" : "2dsphere"})
db.stations.ensureIndex({name: "text", address: "text"})

db.stations.find( { $text: { $search: "MOULIN" } } )


#######################
# Call services #
#######################

curl http://localhost:8080/rest/contract/load
curl http://localhost:8080/rest/contract/list

curl http://localhost:8080/rest/station/monitor
curl "http://localhost:8080/rest/station/geoSearch?latitude=48.873429&longitude=2.30825&radius=200"