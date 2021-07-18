import React from 'react'
import { View, Text,Button,StyleSheet,Dimensions,TextInput,TouchableOpacity} from 'react-native';
import email from 'react-native-email'

export default class DeleteUser extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          email: this.props.route.params.email,
          }
        }

    componentDidMount(){
        fetch('http://192.168.1.9:8080/users/'+this.props.route.params.email+'/ammonizioni', {
            method: 'PUT',
            headers: {
              Accept: 'application/json',
              'Content-Type': 'application/json',
            },
          });
    }

    handleEmail = () => {
        const to = this.props.route.params.email // string or array of email addresses
        email(to, {
            subject: 'AMMONIZIONE JUSTMEET',
            body: 'Sei stato ammonito dal moderatore di JustMeet per comportamenti scorretti'
        }).catch(console.error)
        this.props.navigation.navigate("Admin")
    }


    render(){
        return (
            <View>
                 <Button
                    title="Fatto"
                    onPress={() => this.handleEmail()}
        />
            </View>
        )
    
    }
}