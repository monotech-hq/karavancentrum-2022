
(ns app.storage.frontend.file-uploader.side-effects
    (:require [app.storage.frontend.file-uploader.views :as file-uploader.views]
              [dom.api                                  :as dom]
              [tools.temporary-component.api            :as temporary-component]
              [x.app-core.api                           :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn any-file-selected?
  []
  (let [file-selector (dom/get-element-by-id "storage--file-selector")]
       (dom/file-selector->any-file-selected? file-selector)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-file-selector!
  [uploader-id uploader-props]
  (temporary-component/append-component! [file-uploader.views/file-selector uploader-id uploader-props]
                                        #(-> "storage--file-selector" dom/get-element-by-id .click)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-fx :storage.file-uploader/open-file-selector! open-file-selector!)
