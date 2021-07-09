import React from "react";
import {StyleSheet,View, Text} from "react-native"; 
import MapView from 'react-native-maps';
import * as Permissions from 'expo-permissions';
import * as Location from 'expo-location';

export default class GoogleMaps extends React.Component {

  constructor(props) {
  super(props);
  this.state = {
    loading: true,
    dataSource:[],
    error: null,
    latitude: 0,
    longitude: 0
    };
  }

  // DEVICE
  _getLocation = async() => {
    const { status } = await Permissions.askAsync(Permissions.LOCATION);
    if(status !== 'granted'){
      console.log("Permission not granted");
    
    this.setState({
      error: "PERMISSION NOT GRANTED"
    })
  }

  const userLocation = await Location.getCurrentPositionAsync();

  this.setState({
    latitude: userLocation.coords.latitude
  })

  this.setState({
    longitude: userLocation.coords.longitude
  })

  this.getAllEvents()
}



getAllEvents = () => {
  fetch("http://192.168.1.9:8080/events")
  .then(response => response.json())
  .then((responseJson)=> {
    this.setState({
    loading: false,
    dataSource: responseJson
    })
  }).catch(error=>console.log(error))
}


  componentDidMount(){
    //DEVICE
    this._getLocation()

    //EMULATORE
    //this.getAllEvents()
  }
  
  
  render(){
      return(

    <View style={styles.container}>        
    <MapView style={styles.map}
        showsUserLocation= {true}
        region={{
            //DEVICE
            latitude: this.state.latitude,
            longitude: this.state.longitude,
            
            //EMULATORE 
            //latitude: 43.40528,
            //longitude: 13.54809,

            latitudeDelta: 1.0,
            longitudeDelta: 1.0
        }}>

    {this.state.dataSource.map(event => 
        <MapView.Marker 
          key = {event.id}
          coordinate={{
          latitude: event.location.latitudine,
          longitude: event.location.longitudine,
          latitudeDelta: 0.4,
          longitudeDelta: 0.4
        }}
        title = {event.title}
        description = {event.description}/>
    )}
    </MapView> 
    </View>
    );
  }
  
}

  const styles = StyleSheet.create({
    container: {
        position: 'absolute',
        top: 0,
        left:0,
        bottom:0,
        right:0,
        justifyContent: 'flex-end',
        alignItems: 'center'
    },
    map:{
        position:'absolute',
        top:0,
        left:0,
        bottom:0,
        right:0
    }
  }
  );