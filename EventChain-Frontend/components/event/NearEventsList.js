import React from "react";
import {
Image,
Button,
Alert,
Dimensions,
StyleSheet,
View,
ActivityIndicator,
FlatList,
Text,
TouchableOpacity
} from "react-native";
import {Card,Icon} from 'react-native-elements' 
import * as Permissions from 'expo-permissions';
import * as Location from 'expo-location';

export default class NearEventsList extends React.Component {

  constructor(props) {
  super(props);
  this.state = {
    photoPath: "",
    loading: true,
    dataSource:[],
    participantName : this.props.route.params.fullName,
    
    //userPermissions
    errorMessage: '',
    locationX: 0, //latitudine
    locationY: 0   //longitudine
    };
  }


 
  
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

    this.nearEventListFunc();
  }

  nearEventListFunc(){
    // DEVICE 
    fetch("http://192.168.1.9:8080/events/locations/"+this.state.locationX+"/"+this.state.locationY)
    
    //EMULATORE
    //fetch("http://192.168.1.9:8080/events/locations/43.40528/13.54809")
    

  .then(response => response.json())
  .then((responseJson)=> {
    this.setState({
    loading: false,
    dataSource: responseJson
    })
  })
  .catch(error=>console.log(error)) //to catch the errors if any
  }

  componentDidMount(){
  this._getLocation();
  }

  FlatListItemSeparator = () => {
  return (
    <View style={{
      height: .5,
      width:"100%",
      backgroundColor:"rgba(0,0,0,0.5)",
  }}
  />
  );
  }
  renderItem=(data)=> 
    
  <Card
  title={data.item.title}
  image={imageMap[data.item.topic]}
  >
    
  <Text style={{marginBottom: 10}}>
    {data.item.description}{"\n\n"}
    Limite partecipanti : {data.item.numPartecipanti}
  </Text>
  <View style={{ flexDirection: "row" }}>
     <View style={{ flex: 1 }}>
         <TouchableOpacity style={styles.buttonPartecipa }
               onPress = {() => this.props.navigation.navigate('PostPartecipante',{
                          title: data.item.title,
                          idEvento : data.item.id,
                          fullName: this.state.participantName,
                          email: this.props.route.params.email})}>
              <Text style={styles.text}>Partecipa</Text>
         </TouchableOpacity>
     </View>
     <View style={{borderLeftWidth: 1,borderLeftColor: 'white'}}/>
     <View style={{ flex: 1}}>
         <TouchableOpacity style={styles.buttonPartecipa}
                            onPress = {() => Alert.alert("Info Evento",
                                              "Dove: "+data.item.location.nome+"\n\n"+
                                              "Quando: "+data.item.date+"\n\n"+
                                              "Partecipanti: "+data.item.participants+"\n\n"+
                                              "Organizzatore: "+data.item.organizzatore+"\n\n"+
                                              "Categoria: "+data.item.topic)}>
              <Text style={styles.text}>Info</Text>
         </TouchableOpacity>
     </View>
     </View>
  </Card>
  

  render(){
  if(this.state.loading){
    return( 
      <View style={styles.loader}> 
        <ActivityIndicator size="large" color="#0c9"/>
      </View>
  )}
  if(this.state.dataSource.length == 0){
    return(
    <View>
        <Text style = {styles.instructions}>
          Al momento non ci sono eventi disponibili nel raggio di 100 chilometri.{"\n"}{"\n"}{"\n"}
          La tua posizione:{"\n"} latitudine:{this.state.locationX}, longitudine:{this.state.locationY}
        </Text>
    </View>
    )}
  return(
  <FlatList
      data= {this.state.dataSource}
      ItemSeparatorComponent = {this.FlatListItemSeparator}
      renderItem= {item=> this.renderItem(item)}
      keyExtractor= {item=>item.id.toString()}
  />
  )}
  }

  var imageMap = {
    'AMICI' : require('../images/AMICI.jpg'),
    'GENERALE':  require('../images/GENERALE.jpg'),
    'PARTY':  require('../images/PARTY.jpg'),
    'SPORT':  require('../images/SPORT.jpg'),
    'STUDIO':  require('../images/STUDIO.jpg')
  }

  const { width: WIDTH } = Dimensions.get('window')

  const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: "#fff"
    },
    loader:{
      flex: 1,
      justifyContent: "center",
      alignItems: "center",
      backgroundColor: "#fff"
    },
    list:{
      paddingVertical: 4,
      margin: 5,
      backgroundColor: "#fff"
    },
    buttonPartecipa: {
      alignSelf: 'stretch',
      backgroundColor: '#2980B9'
    },
    buttonInfo: {
      width: WIDTH - 55,
      backgroundColor: '#448AFF',
      alignItems: 'flex-end',
    },
    text: {
      alignSelf: 'center',
      color: '#ffffff',
      fontSize: 16,
      fontWeight: '600',
      paddingTop: 10,
      paddingBottom: 10 
    },
    instructions: {
      fontSize: 16,
      textAlign: 'center',
      color: '#333333',
      marginBottom: 5,
      marginTop: 250
    }
  });