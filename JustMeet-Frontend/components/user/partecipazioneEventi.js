import React from "react";
import {
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

export default class PartecipazioneEventi extends React.Component {

  constructor(props) {
  super(props);
  this.state = {
    loading: true,
    dataSource:[],
    participantName : this.props.route.params.fullName
    };
  }
  componentDidMount(){
    fetch("http://192.168.1.9:8080/users/"+this.props.route.params.email+"/participantEvent")
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
  title={data.item.title}
  image={imageMap[data.item.topic]}
  >
  <Text style={{marginBottom: 10}}>
    {data.item.description}{"\n\n"}
    Limite partecipanti : {data.item.numPartecipanti}
  </Text>
     <View style={{ flex: 1 }}>
         <TouchableOpacity style={styles.button }
               onPress = {() => Alert.alert(  
                'Annulla Partecipazione',  
                'Confermi di voler annullare la partecipazione?',  
                [  
                    {  
                        text: 'No',    
                        style: 'cancel',  
                    },  
                    {text: 'Si',
                     onPress: () =>   this.props.navigation.navigate('Annulla Partecipazione',{
                      title : data.item.title,
                      idEvento: data.item.id,
                      participantName: this.state.participantName,
                      email: this.props.route.params.email})},  
                ]  
            )}>

              <Text style={styles.text}>Annulla Partecipazione</Text>
         </TouchableOpacity>
     </View>
     <View style={{borderLeftWidth: 1,borderLeftColor: 'white'}}/>
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
            <Text style={styles.instructions}>
                Non ti sei prenotato a nessun evento.
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
    button: {
      alignSelf: 'stretch',
      backgroundColor: '#2980B9',
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
    instructions: {
      fontSize: 16,
      textAlign: 'center',
      color: '#333333',
      marginBottom: 5,
      marginTop: 250
    }
  });