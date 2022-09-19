
(ns app.common.frontend.api
    (:require [app.common.frontend.config-editor.views :as config-editor.views]
              [app.common.frontend.context-menu.views  :as context-menu.views]
              [app.common.frontend.credits.views       :as credits.views]
              [app.common.frontend.item-browser.views  :as item-browser.views]
              [app.common.frontend.item-editor.views   :as item-editor.views]
              [app.common.frontend.item-lister.views   :as item-lister.views]
              [app.common.frontend.item-selector.views :as item-selector.views]
              [app.common.frontend.item-viewer.views   :as item-viewer.views]
              [app.common.frontend.popup.views         :as popup.views]
              [app.common.frontend.surface.views       :as surface.views]))

;; -- Redirects ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

; app.common.frontend.config-editor.views
(def config-editor-breadcrumbs config-editor.views/config-editor-breadcrumbs)
(def config-editor-label-bar   config-editor.views/config-editor-label-bar)
(def config-editor-menu-bar    config-editor.views/config-editor-menu-bar)
(def config-editor-action-bar  config-editor.views/config-editor-action-bar)
(def config-editor-ghost-view  config-editor.views/config-editor-ghost-view)

; app.common.frontend.context-menu.views
(def context-menu-label-bar context-menu.views/context-menu-label-bar)

; app.common.frontend.credits.views
(def copyright-label  credits.views/copyright-label)
(def mt-logo          credits.views/mt-logo)
(def created-by-label credits.views/created-by-label)
(def created-by       credits.views/created-by)
(def credits          credits.views/credits)

; app.common.frontend.item-browser.views
(def item-browser-search-block item-browser.views/item-browser-search-block)
(def item-browser-breadcrumbs  item-browser.views/item-browser-breadcrumbs)
(def item-browser-label-bar    item-browser.views/item-browser-label-bar)

; app.common.frontend.item-editor.views
(def item-editor-breadcrumbs item-editor.views/item-editor-breadcrumbs)
(def item-editor-label-bar  item-editor.views/item-editor-label-bar)
(def item-editor-menu-bar   item-editor.views/item-editor-menu-bar)
(def item-editor-action-bar item-editor.views/item-editor-action-bar)
(def item-editor-image-list item-editor.views/item-editor-image-list)
(def item-editor-ghost-view item-editor.views/item-editor-ghost-view)

; app.common.frontend.item-lister.views
(def list-item-structure       item-lister.views/list-item-structure)
(def list-item-thumbnail       item-lister.views/list-item-thumbnail)
(def list-item-thumbnail-icon  item-lister.views/list-item-thumbnail-icon)
(def list-item-label           item-lister.views/list-item-label)
(def list-item-details         item-lister.views/list-item-details)
(def list-item-detail          item-lister.views/list-item-detail)
(def list-item-primary-cell    item-lister.views/list-item-primary-cell)
(def list-item-end-icon        item-lister.views/list-item-end-icon)
(def item-lister-breadcrumbs   item-lister.views/item-lister-breadcrumbs)
(def item-lister-label-bar     item-lister.views/item-lister-label-bar)
(def item-lister-search-block  item-lister.views/item-lister-search-block)
(def item-lister-header-spacer item-lister.views/item-lister-header-spacer)
(def item-lister-header-cell   item-lister.views/item-lister-header-cell)
(def item-lister-header        item-lister.views/item-lister-header)
(def item-lister-wrapper       item-lister.views/item-lister-wrapper)
(def item-lister-ghost-view    item-lister.views/item-lister-ghost-view)

; app.common.frontend.item-selector.views
(def item-selector-footer      item-selector.views/item-selector-footer)
(def item-selector-control-bar item-selector.views/item-selector-control-bar)

; app.common.frontend.item-viewer.views
(def item-viewer-item-info   item-viewer.views/item-viewer-item-info)
(def item-viewer-breadcrumbs item-viewer.views/item-viewer-breadcrumbs)
(def item-viewer-label-bar   item-viewer.views/item-viewer-label-bar)
(def item-viewer-menu-bar    item-viewer.views/item-viewer-menu-bar)
(def item-viewer-action-bar  item-viewer.views/item-viewer-action-bar)
(def item-viewer-image-list  item-viewer.views/item-viewer-image-list)
(def item-viewer-ghost-view  item-viewer.views/item-viewer-ghost-view)

; app.common.frontend.popup.views
(def popup-label-bar          popup.views/popup-label-bar)
(def popup-progress-indicator popup.views/popup-progress-indicator)

; app.common.frontend.surface.views
(def go-back-button      surface.views/go-back-button)
(def surface-label       surface.views/surface-label)
(def surface-description surface.views/surface-description)
(def surface-breadcrumbs surface.views/surface-breadcrumbs)
