import React from "react";
import {
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

export default class ListaEventi extends React.Component {

  constructor(props) {
  super(props);
  this.state = {
    loading: true,
    dataSource:[]
    };
  }
  componentDidMount(){
  fetch("http://192.168.1.9:8080/users")
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
  title={data.item.fullName}
  image= {{uri: data.item.photoUrl}}
  >
  <Text style={{marginBottom: 10}}>
    {data.item.fullName}    {data.item.email}  {"\n\n"}
    Ammonizioni: {data.item.ammonizioni}
  </Text>
  <View style={{ flexDirection: "row" }}>
     <View style={{ flex: 1 }}>
         <TouchableOpacity style={styles.buttonPartecipa }
               onPress = {() => this.props.navigation.navigate('Ammonisci Utente [Admin]',{
                          email: data.item.email
                })}>
              <Text style={styles.text}>Ammonisci Utente</Text>
         </TouchableOpacity>
     </View>
     <View style={{borderLeftWidth: 1,borderLeftColor: 'white'}}/>
     <View style={{ flex: 1}}>
         <TouchableOpacity style={styles.buttonPartecipa}
                            onPress = {() => Alert.alert("Info Utente",
                                              "Eventi Creati: "+data.item.eventiCreati+"\n\n"+
                                              "Partecipa a: "+data.item.partecipazioneEventi+"\n\n"
                            )}>
              <Text style={styles.text}>Info Utente</Text>
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
      ItemSeparatorComponent = {this.FlatListItemSeparator}
      renderItem= {item=> this.renderItem(item)}
      keyExtractor= {item=>item.id.toString()}
  />
  )}
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