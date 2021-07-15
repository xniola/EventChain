import React from 'react'
import {
    Alert,
    StyleSheet,
    View,
    ImageBackground,
    Text,
    TouchableOpacity
    } from "react-native";
    import {Card,Icon} from 'react-native-elements' 

export default class InfoEvento extends React.Component{
    render(){
        return(
            <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
            <View>
                <Text style={styles.text}>
                    Luogo:{"\n"} {this.props.route.params.nomeLocation}{"\n"}{"\n"}
                    Data : {"\n"}{this.props.route.params.date}{"\n"}{"\n"}
                    organizzatore:{"\n"} {this.props.route.params.organizzatore}{"\n"}{"\n"}
                    topic: {"\n"}{this.props.route.params.topic}{"\n"}{"\n"}
                    adesioni attuali: {"\n"}{this.props.route.params.adesioniAttuali}{"\n"}{"\n"}
                </Text>

                <TouchableOpacity style={styles.button}
                              onPress = {() => Alert.alert("Partecipanti",JSON.stringify(this.props.route.params.participants))}>
                        <Text style={styles.text}>Visualizza Partecipanti</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.button}
                              onPress = {() => this.props.navigation.navigate("Scrivi Commento",{
                                idEvento : this.props.route.params.id,
                                email: this.props.route.params.email,
                                idEvento: this.props.route.params.id,
                                photo: this.props.route.params.photo,
                                title: this.props.route.params.title
                            })}>
                        <Text style={styles.text}>Pubblica un Commento</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.button}
                              onPress = {() => this.props.navigation.navigate("Sezione Commenti",{
                                idEvento : this.props.route.params.id,
                                commenti: this.props.route.params.commento
                            })}>
                        <Text style={styles.text}>Vedi Commenti</Text>
                </TouchableOpacity>
            </View>
            </ImageBackground>
        )
    }
}

const styles = StyleSheet.create({
    text: {
        color: 'black',
        textAlign: 'center',
        fontSize: 20,
        fontWeight: 'bold'
      },
      button: {
        alignSelf: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(142, 68, 173, 0.2)',
        padding: 20,
        marginTop: 10,
        width: 300,
        borderRadius:50,
      },
      image: {
        flex: 1,
        resizeMode: "cover",
        justifyContent: "center",
        alignItems: 'center',
        width: '100%',
        height: '100%'
      }
})