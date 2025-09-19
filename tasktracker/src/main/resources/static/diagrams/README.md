Diagrams README

This folder contains exported diagram images for the project's Mermaid diagrams. The images are placeholders and can be regenerated locally with the Mermaid CLI or with the online Mermaid Live Editor.

To regenerate locally (recommended):

1. Install Node.js and npm if you don't have them.
2. Install mermaid-cli:

   npm install -g @mermaid-js/mermaid-cli

3. Create a `.mmd` file containing the Mermaid diagram (copy blocks from `ARCHITECTURE_AND_FLOW.md`).
4. Run mmdc to export PNG/SVG:

   mmdc -i diagram.mmd -o diagram.png

Replace `diagram.mmd` with the filename and `diagram.png` with desired output.
