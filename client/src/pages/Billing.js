import React from "react";
import {Box, Heading, SimpleGrid, Text} from "@chakra-ui/react";
import FeatureCard from "../components/home/FeatureCard";


function Billing() {
    return (
        <Box mt={3}>
            <Heading
                pl={5}
                fontWeight={"semibold"}
                size={"xl"}
                color={"#333"}
                textAlign={"center"}
            >
                Welcome to Sri Tel Service
            </Heading>
            <SimpleGrid
                columns={{sm: 1, md: 2, lg: 3}}
                spacing={10}
                mt={10}
                ml={5}
                mr={5}
            >
                <FeatureCard title={"User Management"} description={"Manage your users"} buttonText={"Manage"} url={"/users"}/>
                <FeatureCard title={"Service Management"} description={"Manage your services"} buttonText={"Manage"} url={"/services"}/>
                <FeatureCard title={"Billing Service"} description={"Manage your billing"} buttonText={"Manage"} url={"/billing"}/>
                <FeatureCard title={"Chat Service"} description={"Manage your chat"} buttonText={"Manage"} url={"http://localhost:8089"} ext={true}/>
                <FeatureCard title={"Notification Service"} description={"Manage your notifications"} buttonText={"Manage"} url={"http://localhost:8088"} ext={true}/>
            </SimpleGrid>
        </Box>
    );
}

export default Billing;
