package com.mumayank.airwebview.helpers

import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.File

object PdfViewHelper {

    fun init(pdfView: PDFView, file: File): PDFView {
        return pdfView.apply {
            this.fromFile(file)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(Constants.ZERO)
                //.onRender(onRenderListener) // called after document is rendered for the first time
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(Constants.ZERO)
                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
                .linkHandler(DefaultLinkHandler(pdfView))
                .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                .pageSnap(false) // snap pages to screen boundaries
                .pageFling(false) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
        }
    }

}