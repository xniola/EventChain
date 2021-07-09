import React from "react";
import {
Dimensions,
StyleSheet,
View,
ActivityIndicator,
FlatList,
Text,
TouchableOpacity
} from "react-native";
import {Card,ListItem} from 'react-native-elements' 

export default class CommentiPubblicati extends React.Component {

  constructor(props) {
  super(props);
  this.state = {
    loading: true,
    dataSource:[],
    };
  }
  componentDidMount(){
  fetch("http://192.168.1.9:8080/users/"+this.props.route.params.email+"/comments")
  .then(response => response.json())
  .then((responseJson)=> {
    this.setState({
    loading: false,
    dataSource: responseJson
    })
  }).catch(function(error) {
    console.log('There has been a problem with your fetch operation: ' + error.message);
     // ADD THIS THROW error
      throw error;
    })
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
  <Card containerStyle={{padding: 0}} >
        <ListItem
          key={data.item.id}
          title={data.item.body+"  "+data.item.orarioPubblicazione}
          leftAvatar={{ source: { uri: data.item.photoMittente} }}
        />
        <View style={{ flexDirection: "row" }}>
            <View style={{ flex: 1 }}>
                <TouchableOpacity style={styles.buttonModifica}
                                  onPress = {() => this.props.navigation.navigate('Modifica Commento',{
                                  id: data.item.id
                                })}>
                            <Text style={styles.text}>Modifica Commento</Text>
                </TouchableOpacity>
            </View>
        <View style={{borderLeftWidth: 1,borderLeftColor: 'white'}}/>
     <View style={{ flex: 1}}>
         <TouchableOpacity style={styles.buttonCancella}
                           onPress = {() => this.props.navigation.navigate('Cancella Commento',{
                           id: data.item.id
                           })}>
                <Text style={styles.text}>Cancella Commento</Text>
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
          Al momento non hai pubblicato nessun commento.
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
    text: {
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
    },
    buttonModifica: {
        alignSelf: 'center',
        backgroundColor: '#DDDDDD',
        borderRadius: 50,
        marginBottom: 10,
        width: 150
      },
      buttonCancella: {
        alignSelf: 'center',
        backgroundColor: 'rgba(236, 100, 75, 1)',
        borderRadius: 50,
        marginBottom: 10,
        width:150
      }
  });