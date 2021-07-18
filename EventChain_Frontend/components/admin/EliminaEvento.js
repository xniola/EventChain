import React from 'react'
import { View, Text,Button,StyleSheet,Dimensions,TextInput,TouchableOpacity} from 'react-native';

export default class EliminaEvento extends React.Component {
    componentDidMount(){
        fetch('http://192.168.1.9:8080/events/'+this.props.route.params.idEvento, {
            method: 'DELETE',
            headers: {
              Accept: 'application/json',
              'Content-Type': 'application/json',
            },
          });
    }


    render(){
        return (
            <View>
                 <Button
                    title="Evento eliminato!"
                    onPress={() => this.props.navigation.navigate("Admin")}
        />
            </View>
        )
    
    }
}