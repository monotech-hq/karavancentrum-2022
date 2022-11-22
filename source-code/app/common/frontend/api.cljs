
(ns app.common.frontend.api
    (:require [app.common.frontend.item-picker.subs]
              [app.common.frontend.item-selector.effects]
              [app.common.frontend.item-selector.events]
              [app.common.frontend.action-bar.views            :as action-bar.views]
              [app.common.frontend.data-table.views            :as data-table.views]
              [app.common.frontend.file-editor.views           :as file-editor.views]
              [app.common.frontend.item-browser.views          :as item-browser.views]
              [app.common.frontend.item-editor.views           :as item-editor.views]
              [app.common.frontend.item-lister.views           :as item-lister.views]
              [app.common.frontend.item-preview.views          :as item-preview.views]
              [app.common.frontend.item-picker.views           :as item-picker.views]
              [app.common.frontend.item-selector.subs          :as item-selector.subs]
              [app.common.frontend.item-selector.views         :as item-selector.views]
              [app.common.frontend.item-viewer.views           :as item-viewer.views]
              [app.common.frontend.menu-header.views           :as menu-header.views]
              [app.common.frontend.pdf-preview.views           :as pdf-preview.views]
              [app.common.frontend.selector-item-counter.views :as selector-item-counter.views]
              [app.common.frontend.selector-item-marker.views  :as selector-item-marker.views]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; app.common.frontend.action-bar.views
(def action-bar action-bar.views/element)

; app.common.frontend.data-table.views
(def data-table data-table.views/element)

; app.common.frontend.file-editor.views
(def file-editor-menu-bar file-editor.views/file-editor-menu-bar)
(def file-editor-controls file-editor.views/file-editor-controls)

; app.common.frontend.item-browser.views
(def item-browser-search-field       item-browser.views/item-browser-search-field)
(def item-browser-search-description item-browser.views/item-browser-search-description)
(def item-browser                    item-browser.views/element)

; app.common.frontend.item-editor.views
(def item-editor-color-picker  item-editor.views/item-editor-color-picker)
(def item-editor-menu-bar      item-editor.views/item-editor-menu-bar)
(def item-editor-controls      item-editor.views/item-editor-controls)

; app.common.frontend.item-lister.views
(def list-item-structure            item-lister.views/list-item-structure)
(def list-item-label                item-lister.views/list-item-label)
(def list-item-details              item-lister.views/list-item-details)
(def list-item-detail               item-lister.views/list-item-detail)
(def list-item-primary-cell         item-lister.views/list-item-primary-cell)
(def item-lister-search-field       item-lister.views/item-lister-search-field)
(def item-lister-search-description item-lister.views/item-lister-search-description)
(def item-lister-header-spacer      item-lister.views/item-lister-header-spacer)
(def item-lister-header-cell        item-lister.views/item-lister-header-cell)
(def item-lister-header             item-lister.views/item-lister-header)
(def item-lister-wrapper            item-lister.views/item-lister-wrapper)
(def item-lister-create-item-button item-lister.views/item-lister-create-item-button)
(def item-lister-download-info      item-lister.views/item-lister-download-info)
(def item-lister                    item-lister.views/element)

; app.common.frontend.item-picker.views
(def item-picker item-picker.views/element)

; app.common.frontend.item-preview.views
(def item-preview item-preview.views/element)

; app.common.frontend.item-selector.views
(def item-selector-footer      item-selector.views/item-selector-footer)
(def item-selector-control-bar item-selector.views/item-selector-control-bar)
(def item-selector-body        item-selector.views/item-selector-body)

; app.common.frontend.item-viewer.views
(def item-viewer-item-info item-viewer.views/item-viewer-item-info)
(def item-viewer-menu-bar  item-viewer.views/item-viewer-menu-bar)
(def item-viewer-controls  item-viewer.views/item-viewer-controls)

; app.common.frontend.selector-item-counter.views
(def selector-item-counter selector-item-counter.views/element)

; app.common.frontend.selector-item-marker.views
(def selector-item-marker selector-item-marker.views/element)

; app.common.frontend.menu-header.views
(def menu-header menu-header.views/element)

; app.common.frontend.pdf-preview.views
(def pdf-preview pdf-preview.views/element)
