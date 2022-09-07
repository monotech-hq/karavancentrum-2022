
(ns app.common.frontend.api
    (:require [app.common.frontend.browser.views  :as browser.views]
              [app.common.frontend.credits.views  :as credits.views]
              [app.common.frontend.editor.views   :as editor.views]
              [app.common.frontend.lister.views   :as lister.views]
              [app.common.frontend.popup.views    :as popup.views]
              [app.common.frontend.selector.views :as selector.views]
              [app.common.frontend.surface.views  :as surface.views]
              [app.common.frontend.viewer.views   :as viewer.views]))

;; -- Redirects ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

; app.common.frontend.browser.views
(def item-browser-label-bar browser.views/item-browser-label-bar)

; app.common.frontend.credits.views
(def copyright-label  credits.views/copyright-label)
(def mt-logo          credits.views/mt-logo)
(def created-by-label credits.views/created-by-label)
(def created-by       credits.views/created-by)
(def credits          credits.views/credits)

; app.common.frontend.editor.views
(def input-block-separator  editor.views/input-block-separator)
(def item-editor-label-bar  editor.views/item-editor-label-bar)
(def item-editor-menu-bar   editor.views/item-editor-menu-bar)
(def item-editor-action-bar editor.views/item-editor-action-bar)
(def item-editor-image-list editor.views/item-editor-image-list)
(def item-editor-ghost-view editor.views/item-editor-ghost-view)

; app.common.frontend.lister.views
(def list-item-structure       lister.views/list-item-structure)
(def list-item-thumbnail       lister.views/list-item-thumbnail)
(def list-item-thumbnail-icon  lister.views/list-item-thumbnail-icon)
(def list-item-label           lister.views/list-item-label)
(def list-item-details         lister.views/list-item-details)
(def list-item-detail          lister.views/list-item-detail)
(def list-item-primary-cell    lister.views/list-item-primary-cell)
(def list-item-end-icon        lister.views/list-item-end-icon)
(def item-lister-label-bar     lister.views/item-lister-label-bar)
(def item-lister-search-block  lister.views/item-lister-search-block)
(def item-lister-header-spacer lister.views/item-lister-header-spacer)
(def item-lister-header-cell   lister.views/item-lister-header-cell)
(def item-lister-header        lister.views/item-lister-header)
(def item-lister-wrapper       lister.views/item-lister-wrapper)
(def item-lister-ghost-view    lister.views/item-lister-ghost-view)

; app.common.frontend.popup.views
(def popup-label-bar        popup.views/popup-label-bar)
(def popup-saving-indicator popup.views/popup-saving-indicator)

; app.common.frontend.selector.views
(def item-selector-footer      selector.views/item-selector-footer)
(def item-selector-control-bar selector.views/item-selector-control-bar)

; app.common.frontend.surface.views
(def surface-label       surface.views/surface-label)
(def surface-description surface.views/surface-description)

; app.common.frontend.viewer.views
(def item-viewer-label-bar  viewer.views/item-viewer-label-bar)
(def item-viewer-menu-bar   viewer.views/item-viewer-menu-bar)
(def item-viewer-action-bar viewer.views/item-viewer-action-bar)
(def item-viewer-ghost-view viewer.views/item-viewer-ghost-view)
