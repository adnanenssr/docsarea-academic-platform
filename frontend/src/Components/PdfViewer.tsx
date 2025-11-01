import React, { useEffect, useState } from 'react';
import { Worker } from '@react-pdf-viewer/core';
// Core viewer
import { Viewer } from '@react-pdf-viewer/core';
// Plugins
import { defaultLayoutPlugin } from '@react-pdf-viewer/default-layout';
import '@react-pdf-viewer/core/lib/styles/index.css';
import '@react-pdf-viewer/default-layout/lib/styles/index.css';
import { toolbarPlugin, type ToolbarSlot, type TransformToolbarSlot } from '@react-pdf-viewer/toolbar';


interface PdfViewerProps {
    fileId: string;
    counter: number ;
}


const PdfViewer: React.FC<PdfViewerProps> = ({ fileId , counter }) => {

    const [pdfUrl, setPdfUrl] = useState(null);
    useEffect(() => {
        if (!fileId) {
    console.warn("No fileId provided to PdfViewer");
    return;
  }

  const fetchPdf = async () => {
    const res = await fetch(`http://localhost:8082/file/${fileId}`, {
      method: 'GET',
      credentials: 'include', // 👈 this sends cookies along
    });
    console.log(res.headers.get("content-type"));


    if (!res.ok) {
      throw new Error(`Failed to fetch PDF: ${res.status}`);
    }

    const blob = await res.blob();
    const url = URL.createObjectURL(blob);
    setPdfUrl(url);
  };

  if (fileId) fetchPdf();
}, [fileId , counter ]);


    const toolbarPluginInstance = toolbarPlugin();
    const { renderDefaultToolbar, Toolbar } = toolbarPluginInstance;

    const transform: TransformToolbarSlot = (slot: ToolbarSlot) => ({
    ...slot,
    // These slots will be empty
    Download: () => <></>,
    Open: () => <></>,
    Print: () => <></>,
});


    // Create new plugin instance with customizations
    const defaultLayoutPluginInstance = defaultLayoutPlugin({
        // Remove sidebar completely
        sidebarTabs: () => [],
        
        // Customize toolbar to remove download, open, print
        renderToolbar: (Toolbar) => (
            <Toolbar>{renderDefaultToolbar(transform)}</Toolbar>
        ),
    });

    return (
        <div key={'viewer'} style={{ height: '100vh' }}>
            {pdfUrl && <Viewer 
fileUrl={pdfUrl}
plugins={[
    // Register plugins
    defaultLayoutPluginInstance,
]}

/>}
        </div>
    );
};

export default PdfViewer;