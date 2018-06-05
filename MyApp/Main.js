import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View,
  Button
} from 'react-native';

export default class Main extends Component{
	static navigationOptions = ({ navigation }) => {
		const params = navigation.state.params || {};

		return {
			title: 'Main Frame',
			headerLeft: null,
			headerRight: null
			/* headerRight: (
			  <Button
				onPress={params.increaseCount}
				title="+1"
				color="#fff"
			  />
			), */
		};
	}
	
	componentWillMount(){
		this.props.navigation.setParams({
			increaseCount: this._increaseCount
		});
	}
	
	state = {
		count: 0,
	};
	
	_increaseCount = () => {
		this.setState({
			count: this.state.count + 1
		});	
	};
	
	render(){
		/* Read the params from the navigation state */
		const { params } = this.props.navigation.state;
		const userId = params ? params.userId : '';
		
		return (
			<View style={styles.container}>
				<Text>Login Account: {userId}</Text>
				<Text>Current number: {this.state.count}</Text>
				<Button title="About"
					onPress = {() => this.props.navigation.navigate('About')} />
			</View>
		);
	}
}

const styles = StyleSheet.create({
	container: {
		flex: 1, 
		alignItems: 'center', 
		justifyContent: 'center'
	}
});