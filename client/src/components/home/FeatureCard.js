import React from "react";
import {
	Card,
	CardHeader,
	Heading,
	CardBody,
	Text,
	CardFooter,
	Button,
	Flex,
	Stack,
	StackDivider,
	Box,
	TagRightIcon,
} from "@chakra-ui/react";
import {useNavigate} from "react-router-dom";
import { FiSettings } from "react-icons/fi"; 

function FeatureCard(props) {

    const {title, description, buttonText, url, ext=false } = props;

    const navigate = useNavigate()

    const handleButtonClick = () => {
        window.location.href = url;
    };


    const handleClick = () => {
        if(ext) {
            handleButtonClick()
        } else {
            navigate(url)
        }
    }

    return (
		<>
			<Card border={"1px"} borderColor={"purple.200"}>
				<CardHeader>
					<Heading size="md">{title}</Heading>
				</CardHeader>

				<CardBody>
					<Box>
						<Text size="xs">{description}</Text>
						<Flex mt={10} flexDirection={"column"} w={"100%"}>
							<Button
								onClick={handleClick}
								colorScheme={"purple"}
								_hover={"primary"}
							>
								<FiSettings style={{ marginRight: "0.5rem" }}  />
								{buttonText }
							</Button>
						</Flex>
					</Box>

					<Box></Box>
				</CardBody>
			</Card>
		</>
	);
}

export default FeatureCard;
