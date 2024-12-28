import os
import re

# Paths
#nefs path f application properties
input_file_path = r"testsAndRecomendations\UnitTests.txt"
#path fin aytheto les classes dyal tests
output_folder = r"F:\FRO\all\hotel\src\test\java\ma\hotel"

# Ensure output folder exists
if not os.path.exists(output_folder):
    os.makedirs(output_folder)
    print(f"Created output folder: {output_folder}")
else:
    print(f"Output folder already exists: {output_folder}")

# Load content of the input file
try:
    with open(input_file_path, 'r') as file:
        content = file.read()
except FileNotFoundError:
    raise FileNotFoundError(f"The file at path {input_file_path} does not exist. Please check the path and try again.")

# Regex to extract imports
import_regex = re.compile(r'(import .*?;)', re.MULTILINE)
imports = import_regex.findall(content)

# Join imports into a single string
import_block = "\n".join(imports) + "\n\n" if imports else ""

# Regex to capture entire classes
class_regex = re.compile(r'(public class \w+ \{.*?^\})', re.DOTALL | re.MULTILINE)

# Find all classes in the file
classes = class_regex.findall(content)

if not classes:
    print("No classes found in the input file.")
else:
    print(f"Found {len(classes)} classes in the input file.\n")

    # Save each class to a separate file
    for class_content in classes:
        # Extract class name
        class_name_match = re.search(r'public class (\w+)', class_content)
        class_name = class_name_match.group(1) if class_name_match else "UnknownClass"

        # Define output file path
        output_file_path = os.path.join(output_folder, f"{class_name}.java")

        # Write the class content to a file
        try:
            with open(output_file_path, 'w') as class_file:
                # Write imports and class content
                class_file.write(import_block)
                class_file.write(class_content)
            print(f"Class '{class_name}' written to {output_file_path}")
        except Exception as e:
            print(f"Error writing class '{class_name}' to file: {e}")

print("\nAll classes with imports have been successfully extracted and saved.")
