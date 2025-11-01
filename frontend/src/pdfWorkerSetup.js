import * as pdfjs from 'pdfjs-dist';

// Set up the PDF.js worker
pdfjs.GlobalWorkerOptions.workerSrc = `https://unpkg.com/pdfjs-dist@3.11.174/build/pdf.worker.min.js`;

export default pdfjs;