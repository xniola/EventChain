import React from 'react'
import {
    Image,
    ImageBackground,
    Alert,
    Dimensions,
    StyleSheet,
    View,
    ActivityIndicator,
    FlatList,
    Text,
    TouchableOpacity,
    Icon
} from "react-native"

export default class ProfiloUtente extends React.Component {
  
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      dataSource:[],
      };
    }
    componentDidMount(){
    fetch("http://192.168.1.9:8080/users/"+this.props.route.params.email)
    .then(response => response.json())
    .then((responseJson)=> {
      this.setState({
      loading: false,
      dataSource: responseJson
      })
    })
    .catch(error=>console.log(error)) //to catch the errors if any
    }
    
  
      render(){
      if(this.state.loading){
        return( 
          <View style={styles.loader}> 
            <ActivityIndicator size="large" color="#0c9"/>
          </View>
      )}
      return(
        <ImageBackground source={require("../images/Sfondo.png")} style={styles.image2}>

        <View style = {styles.container}>

        <TouchableOpacity style = {styles.logout}
          onPress = {() => this.props.navigation.replace("Benvenuto")}
        >
          <Image style={styles.image3} source={require("../images/close.png")}/>
        </TouchableOpacity>

        <TouchableOpacity 
          onPress = {() => Alert.alert("Le mie informazioni personali",
            "Nome: "+this.state.dataSource.nome+"\n\n"+
            "Cognome: "+this.state.dataSource.cognome+"\n\n"+
            "Mail: "+this.state.dataSource.email)}
          
        >
          <Image style={styles.image} source={{uri: this.state.dataSource.photoUrl}}/>
        </TouchableOpacity>

        {/*
        <View style={styles.view}>
          <Image style={styles.image} source={{uri: this.state.dataSource.photoUrl}} />  
        </View>
        */}


        <TouchableOpacity style={styles.button}
               onPress = {() => this.props.navigation.navigate('Scelta Eventi Creati',{ 
                email: this.state.dataSource.email
               })}>
              <Text style={styles.text}>Eventi Creati</Text>
         </TouchableOpacity>


         <TouchableOpacity style={styles.button}
           onPress = {() => this.props.navigation.navigate('Scelta Partecipazione Eventi',{
            fullName: this.state.dataSource.fullName, 
            email: this.state.dataSource.email
           })}>
              <Text style={styles.text}>Partecipazioni</Text>
         </TouchableOpacity>

      <TouchableOpacity style={styles.button}
                        onPress={() => this.props.navigation.navigate('Commenti Pubblicati',{
                          email:this.state.dataSource.email
                        })}>
          <Text style = {styles.text}> I miei Commenti </Text>
      </TouchableOpacity>

       
            <TouchableOpacity style={styles.button}
                        onPress={() => this.props.navigation.navigate('Email')}>
          <Text style = {styles.text}> Segnala un problema </Text>
      </TouchableOpacity>
  
     <TouchableOpacity style={styles.button}
                        onPress={() => this.props.navigation.navigate('Elimina Utente',{
                          email: this.state.dataSource.email
                        })}>
          <Text style = {styles.text}> Elimina il mio account </Text>
      </TouchableOpacity>
     
     </View>
     </ImageBackground> 
      )}
    }
   


const { width: WIDTH } = Dimensions.get('window')

const styles = StyleSheet.create({
  view: {
    flex: 1,
    alignItems: 'center',
    alignContent: 'center',
    justifyContent: 'center'
  },
  background:{
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  container: {
    flex: 0.95,
    alignItems: "center",
    justifyContent: "center"
  },
  header: {
    color: 'black',
    fontSize: 20
  },
  image: {
    marginTop: 15,
    width: 150,
    height: 150,
    borderWidth: 3,
    borderRadius: 150
  },
  image2: {
    flex: 1,
    resizeMode: "cover",
    justifyContent: "center",
    alignItems: 'center',
    width: '100%',
    height: '100%'
  },
  image3: {
    marginTop: 10,
    width: 25,
    height: 25,
    borderRadius: 150,
    marginEnd: 300
  },
  text: {
    color: 'black',
    textAlign: 'center',
    fontSize: 20,
    fontWeight: 'bold'
  },
  button: {
    alignItems: 'center',
    //backgroundColor: '#DDDDDD',
    backgroundColor: 'rgba(142, 68, 173, 0.2)',
    padding: 20,
    marginTop: 20,
    borderRadius:70,
    width: 280
  },
  button2:{
    alignSelf: 'stretch',
    backgroundColor: '#2980B9'
  },
  logout: {
    alignContent: 'flex-start'
  }
});
