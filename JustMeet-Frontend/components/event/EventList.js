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

export default class EventList extends React.Component {

  constructor(props) {
  super(props);
  this.state = {
    email: this.props.route.params.email,
    photoPath: "",
    loading: true,
    dataSource:[],
    participantName : this.props.route.params.fullName
    };
  }
  componentDidMount(){
  fetch("http://192.168.1.9:8080/events")
  .then(response => response.json())
  .then((responseJson)=> {
    this.setState({
    loading: false,
    dataSource: responseJson
    })
  })
  .catch(error=>console.log(error)) //to catch the errors if any
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
  style={styles.card}
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
         <TouchableOpacity style={styles.buttonInfo}
                            onPress = {() => this.props.navigation.navigate("Info Evento",{
                              email: this.state.email,
                              id: data.item.id,
                              nomeLocation: data.item.location.nome,
                              date: data.item.date,
                              participants: data.item.participants,
                              organizzatore: data.item.organizzatore,
                              topic: data.item.topic,
                              adesioniAttuali: data.item.adesioniAttuali,
                              commenti: data.item.commento,
                              photo: this.props.route.params.photo,
                              title: data.item.title
                            })}>
              <Text style={styles.text2}>Info</Text>
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
          Al momento non ci sono eventi pubblicati.
        </Text>
    </View>
    )}
  return(
  <FlatList
      data= {this.state.dataSource}
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
      backgroundColor: 'rgba(41, 128, 185,1)',
      borderRadius: 20
    },
    buttonInfo: {
      alignSelf: 'stretch',
      backgroundColor: 'rgba(218, 223, 225, 1)',
      borderRadius: 20
    },
    text: {
      alignSelf: 'center',
      color: '#ffffff',
      fontSize: 16,
      fontWeight: '600',
      paddingTop: 10,
      paddingBottom: 10 
    },
    text2: {
      alignSelf: 'center',
      color: 'black',
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