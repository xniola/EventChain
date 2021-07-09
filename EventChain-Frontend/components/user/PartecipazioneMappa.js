import React from "react";
import {StyleSheet,View, Text} from "react-native"; 
import MapView from 'react-native-maps';
import * as Permissions from 'expo-permissions';
import * as Location from 'expo-location';


export default class PartecipazioneMappa extends React.Component {

  constructor(props) {
  super(props);
  this.state = {
    loading: true,
    dataSource:[],
    errorMessage: '',
    locationX: 0,
    locationY: 0
    };
  }

  // DEVICE
  _getLocation = async() => {
    const { status } = await Permissions.askAsync(Permissions.LOCATION);
    if(status !== 'granted'){
      console.log("Permission not granted");
    

    this.setState({
      errorMessage: "PERMISSION NOT GRANTED"
    })
  }

  const userLocation = await Location.getCurrentPositionAsync();

  this.setState({
    locationX: userLocation.coords.latitude
  })

  this.setState({
    locationY: userLocation.coords.longitude
  })
}

  componentDidMount(){
  //DEVICE
  this._getLocation();
  fetch("http://192.168.1.9:8080/users/"+this.props.route.params.email+"/participantEvent")
  .then(response => response.json())
  .then((responseJson)=> {
    this.setState({
    loading: false,
    dataSource: responseJson
    })
  })
  }
  
  
  render(){
      return(

    <View style={styles.container}>        
    <MapView style={styles.map}
        showsUserLocation= {true}
        region={{
          //DEVICE
          latitude: this.state.locationX,
          longitude: this.state.locationY,
          
          //EMULATORE
          //latitude: 43.40528,
          //longitude: 13.54809,  

          latitudeDelta: 0.9,
          longitudeDelta: 0.9
        }}>

    {this.state.dataSource.map(event => 
        <MapView.Marker 
        key = {event.id}
        coordinate={{
          latitude: event.location.latitudine,
          longitude: event.location.longitudine,
          latitudeDelta: 0.9,
          longitudeDelta: 0.9
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