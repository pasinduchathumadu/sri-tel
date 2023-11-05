import React from "react";
import {Box, Heading, SimpleGrid, Text} from "@chakra-ui/react";
import FeatureCard from "../components/home/FeatureCard";


function Home() {
    return (
		<Box mt={3}>
			<Heading
				pl={5}
				fontWeight={"semibold"}
				size={"xl"}
				color={"#333"}
				textAlign={"center"}
			>
				Welcome to Our Sri Tel Service Hub
			</Heading>
			<SimpleGrid
				columns={{ sm: 1, md: 2, lg: 3 }}
				spacing={10}
				mt={10}
				ml={5}
				mr={5}
			>
				<FeatureCard
					title={"User Dashboard"}
					description={"Manage your user data and profiles"}
					buttonText={"Manage"} 
					url={"/users"}
				/>
				<FeatureCard
					title={"Service Dashboard"}
					description={"Explore and manage your services"}
					buttonText={"Manage"}
					url={"/services"}
				/>
				<FeatureCard
					title={"Billing Center"}
					description={"Track and manage your billing information"}
					buttonText={"Manage"}
					url={"/billing"}
				/>
				<FeatureCard
					title={"Live Chat Support"}
					description={"Connect with our support team in real-time"}
					buttonText={"Connect"}
					url={"http://localhost:8089"}
					ext={true}
				/>
				<FeatureCard
					title={"Notification Center"}
					description={"Stay updated with important notifications"}
					buttonText={"Check"}
					url={"http://localhost:8088"}
					ext={true}
				/>
			</SimpleGrid>
		</Box>
	);
}

export default Home;
