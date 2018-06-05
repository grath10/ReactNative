import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View,
  Button
} from 'react-native';

export default class About extends Component{
	static navigationOptions = {
		title: 'About'
	}
	
	render(){
		let { params } = this.props.navigation.state;
		return (
			<View>
				<Text>春眠不觉晓，处处闻啼鸟。夜来风雨声，花落知多少。</Text>
			</View>
		);
	}
}